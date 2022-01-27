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
import com.daniil.courses.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    UserRepository userRepository;
    AddressRepository addressRepository;
    StoreItemRepository storeItemRepository;
    BasketRepository basketRepository;
    OrderRepository orderRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository,
                           StoreItemRepository storeItemRepository, BasketRepository basketRepository,
                           OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.storeItemRepository = storeItemRepository;
        this.basketRepository = basketRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<AddressDto> getAllAddressesByUser(Integer userId) {

        userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User not found"));

        return addressRepository.findByUserIdAndVisible(userId, true).stream()
                .map(address -> new AddressDto(address.getId(), address.getBase(),
                        address.getCity(), address.getEntrance(),
                        address.getFlat(), address.getFloor(),
                        address.getStreet())).collect(Collectors.toList());
    }

    public void removeAddressByUser(Integer addressId, Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User not found"));
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

        changedAddress.setCity(addressDto.getCity());
        changedAddress.setStreet(addressDto.getStreet());
        changedAddress.setBase(addressDto.getBase());
        changedAddress.setFlat(addressDto.getFlat());
        changedAddress.setFloor(addressDto.getFloor());
        changedAddress.setEntrance(addressDto.getEntrance());

        addressRepository.save(changedAddress);
        return addressDto;
    }

    @Override
    public List<BasketDto> getBasketByUser(Integer userId) {
        return basketRepository.findBasketByUserId(userId).stream()
                .filter(basket -> basket.getStoreItem().isAvailable())
                .map(basket -> new BasketDto(ItemDto.toItemDto(basket.getStoreItem().getItem())
                        , basket.getCount(), basket.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public void addItemToBasketByUser(Integer storeItemId, Integer userId, Integer count) {

        StoreItem storeItem = storeItemRepository.findById(storeItemId).orElseThrow(() -> new StoreItemIsNotFound("store item is not found"));

        if (!storeItem.isAvailable())
            throw new StoreItemIsNotFound("store item is not found");

        basketRepository.save(Basket.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User not found")))
                .storeItem(storeItem)
                .count(count)
                .price(storeItem.getPrice().multiply(BigDecimal.valueOf(count)))
                .build()
        );
    }

    @Override
    public void removeFromBasketByUser(Integer storeItemId, Integer userId) {
        basketRepository.deleteByStoreItemIdAndUserId(storeItemId, userId);
    }

    @Override
    public void clearBasketByUser(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
        basketRepository.deleteAllByUserId(userId);
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
        BigDecimal price = BigDecimal.valueOf(basketList.stream().mapToDouble(basket -> Double.parseDouble(basket.getPrice().toString())).sum());

        List<StoreItem> storeItems = basketRepository.findAllByUserId(userId).stream()
                .map(Basket::getStoreItem).collect(Collectors.toList());

        String externalId = getRandomString();

        orderRepository.save(Order.builder()
                .externalId(externalId)
                .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found")))
                .storeItem(storeItems)
                .address(address)
                .date(LocalDate.now())
                .dateOfRefactoring(LocalDate.now())
                .price(price)
                .status("Ожидает подтверждения платежа")
                .build());

        clearBasketByUser(userId);

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
                                .map(storeItem -> storeItem.getItem().getName())
                                .collect(Collectors.toList())
                ))
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
                .password(userdto.getPassword())
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
}
