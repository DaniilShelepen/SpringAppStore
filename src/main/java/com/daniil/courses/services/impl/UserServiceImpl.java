package com.daniil.courses.services.impl;

import com.daniil.courses.dto.*;
import com.daniil.courses.exceptions.*;
import com.daniil.courses.models.Address;
import com.daniil.courses.models.Basket;
import com.daniil.courses.models.Order;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.payment.PaymentRequest;
import com.daniil.courses.repositories.*;
import com.daniil.courses.role_models.User;
import com.daniil.courses.security.Roles;
import com.daniil.courses.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {


    UserRepository userRepository;
    AddressRepository addressRepository;
    StoreItemRepository storeItemRepository;
    BasketRepository basketRepository;
    OrderRepository orderRepository;
    BasketServiceImpl basketService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository,
                           StoreItemRepository storeItemRepository, BasketRepository basketRepository,
                           OrderRepository orderRepository,BasketServiceImpl basketService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.storeItemRepository = storeItemRepository;
        this.basketRepository = basketRepository;
        this.orderRepository = orderRepository;
        this.basketService=basketService;
    }

    @Override
    public List<AddressDto> getAllAddressesByUser(Integer userId) {


        return addressRepository.findByUserIdAndVisible(userId, true).stream()
                .map(address -> new AddressDto(address.getId(), address.getCity(), address.getStreet(),
                        address.getBase(), address.getFlat(), address.getFloor(), address.getEntrance()))
                .collect(Collectors.toList());
    }

    public void removeAddressByUser(Integer addressId, Integer userId) {
        Address removeAddress = addressRepository.findByUserIdAndIdAndVisible(userId, addressId, true);
        if (removeAddress != null) {
            removeAddress.setVisible(false);
            addressRepository.save(removeAddress);
        } else throw new AddressIsNotFound("Address is not found");
    }

    @Override
    public AddressDto addAddressByUser(Integer userId, AddressDto addressdto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User not found"));
        Address newAddress = Address.builder()
                .user(user)
                .base(addressdto.getBase())
                .city(addressdto.getCity())
                .entrance(addressdto.getEntrance())
                .flat(addressdto.getFlat())
                .floor(addressdto.getFloor())
                .street(addressdto.getStreet())
                .visible(true)
                .build();
        user.setAddresses(List.of(newAddress));
        addressRepository.save(newAddress);
        return addressdto;
    }

    @Override
    public AddressDto refactorAddressByUser(AddressDto addressDto, Integer userId, Integer addressId) {
        Address changedAddress = addressRepository.findByUserIdAndIdAndVisible(userId, addressId, true);
        if (changedAddress == null)
            throw new AddressIsNotFound("Address not found");

        changedAddress.setVisible(false);
        addressRepository.save(changedAddress);

        addressRepository.save(Address.builder()
                .user(changedAddress.getUser())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .base(addressDto.getBase())
                .flat(addressDto.getFlat())
                .floor(addressDto.getFloor())
                .entrance(addressDto.getEntrance())
                .visible(true)
                .build());

        return addressDto;
    }

    @Override
    public List<UserStoreItemDto> viewAvailableItems() {
        return storeItemRepository.findAll().stream()
                .filter(StoreItem::isAvailable)
                .map(storeItem -> new UserStoreItemDto(storeItem.getId(), ItemDto.toItemDto(storeItem.getItem()), storeItem.getPrice()))
                .collect(Collectors.toList());
    }


    @Value("${headers.api.secret}")
    String apiSecret;
    @Value("${bank.url}")
    String bankUrl;
    @Value("${bank.account}")
    String bankAccount;
    @Value("${controller.url}")
    String controllerUrl;


    @Override
    public PaymentRequest buyItems(Integer userId, Integer addressId) {
        basketRepository.findAllByUserId(userId).stream().findAny().orElseThrow(() -> new BasketIsEmpty("Your basket is empty"));
        Address address = addressRepository.findByUserIdAndIdAndVisible(userId, addressId, true);
        if (address == null)
            throw new AddressIsNotFound("Address is not found");
        List<Basket> basketList = basketRepository.findAllByUserId(userId);
        BigDecimal price = BigDecimal.valueOf(basketList.stream().mapToDouble(basketItem -> Double.parseDouble(basketItem.getPrice().toString())).sum());

        List<StoreItem> storeItems = basketRepository.findAllByUserId(userId).stream()
                .map(Basket::getStoreItem).collect(Collectors.toList());

        String externalId = getRandomString();

        orderRepository.save(Order.builder()
                .externalId(externalId)
                .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found")))
                .storeItem(storeItems)
                .address(address)
                .date(LocalDate.now())
                .dateOfRefactoring(new Date())
                .price(price)
                .status("Ожидает подтверждения платежа")
                .build());

        basketService.clearBasketByUser(userId);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("api-secret", apiSecret);

        String requestJson = "{\n" +
                "    \"accountId\": \"" + bankAccount + "\",\n" +
                "    \"amount\": {\n" +
                "        \"currency\": \"USD\",\n" +
                "        \"value\": " + price + "\n" +
                "    },\n" +
                "    \"externalId\": \"" + externalId + "\",\n" +
                "    \"purpose\": \"Payment\",\n" +
                "    \"acquireWebHook\": \"" + controllerUrl + "\"\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        return restTemplate.postForObject(bankUrl, entity, PaymentRequest.class);
    }

    @Override
    public List<UserOrderDto> getAllOrdersByUser(Integer userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(order -> new UserOrderDto(order.getId(), order.getStatus(), order.getDate(), order.getDateOfRefactoring(), order.getPrice(),
                        order.getStoreItem().stream()
                                .map(storeItem -> storeItem.getItem().getName()).collect(Collectors.toList()),
                        AddressDto.toAddressDto(order.getAddress())))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserDto userdto) {

        if (userRepository.findByPhoneNumber(userdto.getPhoneNumber()) != null)
            throw new UserAlreadyExist("User with this phone number already exist!");

        userRepository.save(User.builder()
                .name(userdto.getName())
                .surname(userdto.getSurname())
                .birthday(userdto.getBirthday())
                .password(new BCryptPasswordEncoder().encode(userdto.getPassword()))
                .phoneNumber(userdto.getPhoneNumber())
                .available(true)
                .build());

        return userdto;
    }

    public static String getRandomString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        User DBUser = userRepository.findByPhoneNumber(phoneNumber);

        if (DBUser == null)
            throw new UsernameNotFoundException("User is not found");

        return org.springframework.security.core.userdetails.User.builder()
                .username(DBUser.getPhoneNumber())
                .password(DBUser.getPassword())
                .roles(Roles.USER.toString())
                .build();
    }
}
