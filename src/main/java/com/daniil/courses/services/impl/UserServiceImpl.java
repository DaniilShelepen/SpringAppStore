package com.daniil.courses.services.impl;

import com.daniil.courses.dto.*;
import com.daniil.courses.exceptions.AddressIsNotFound;
import com.daniil.courses.exceptions.StoreItemIsNotFound;
import com.daniil.courses.exceptions.UserAlreadyExist;
import com.daniil.courses.exceptions.UserNotFound;
import com.daniil.courses.models.*;
import com.daniil.courses.repositories.*;
import com.daniil.courses.role_models.User;
import com.daniil.courses.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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
                .map(address -> new AddressDto(address.getBase(),
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
                .map(basket -> new BasketDto(ItemDto.toItemDto(basket.getStoreItem().getItem())
                        , basket.getCount(), basket.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public void addItemToBasketByUser(Integer storeItemId, Integer userId, Integer count) {

        StoreItem storeItem = storeItemRepository.findById(storeItemId).orElseThrow(() -> new StoreItemIsNotFound("store item is not found"));

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
        basketRepository.deleteAllByUserId(userId);
    }

    @Override
    public List<UserStoreItemDto> viewAvailableItems() {
        return storeItemRepository.findAll().stream()
                .filter(StoreItem::isAvailable)
                .map(storeItem -> new UserStoreItemDto(ItemDto.builder()
                        .name(storeItem.getItem().getName())
                        .description(storeItem.getItem().getDescription())
                        .type(storeItem.getItem().getType())
                        .driverConfiguration(storeItem.getItem().getDriverConfiguration())
                        .CPU(storeItem.getItem().getCPU())
                        .releaseDate(storeItem.getItem().getReleaseDate())
                        .build()
                        , storeItem.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public void buyItems(Integer... id) {

    }

    @Override
    public List<UserOrder> getAllOrdersByUser(Integer userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(order -> new UserOrder(order.getStatus(), order.getDate(), order.getDateOfRefactoring()))
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
                .build());
        return userdto;
    }
}
