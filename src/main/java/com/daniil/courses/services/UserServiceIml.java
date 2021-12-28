package com.daniil.courses.services;

import com.daniil.courses.models.*;
import com.daniil.courses.repositories.AddressRepository;
import com.daniil.courses.repositories.BasketRepository;
import com.daniil.courses.repositories.StoreItemRepository;
import com.daniil.courses.repositories.UserRepository;
import com.daniil.courses.role_models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService {


    UserRepository userRepository;
    AddressRepository addressRepository;
    StoreItemRepository storeItemRepository;
    BasketRepository basketRepository;

    @Autowired
    public UserServiceIml(UserRepository userRepository, AddressRepository addressRepository,
                          StoreItemRepository storeItemRepository, BasketRepository basketRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.storeItemRepository = storeItemRepository;
        this.basketRepository = basketRepository;
    }

    @Override
    public List<Address> getAllUserAddresses(Integer userId) {
        return addressRepository.getAddressByUserId(userId);
    }

    @Override
    public void removeAddress(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public List<Address> addAddress(User user, Address address) {
        user.setAddresses(List.of(address));
        addressRepository.save(address);
        return addressRepository.findAll().stream()
                .filter(address1 -> address1.getUser().getId()
                        .equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Address refactorAddress(Address address) {

        Address changedAddress = addressRepository.findById(address.getId()).orElseThrow(RuntimeException::new);//TODO свою тут выкинь

        changedAddress.setCity(address.getCity());
        changedAddress.setStreet(address.getStreet());
        changedAddress.setBase(address.getBase());
        changedAddress.setFlat(address.getFlat());
        changedAddress.setFloor(address.getFloor());
        changedAddress.setEntrance(address.getEntrance());

        return addressRepository.save(changedAddress);
    }

    @Override
    public List<StoreItem> getUserBasket(User user) {
        return basketRepository.getBasketByUserId(user.getId()).stream()
                .map(Basket::getStoreItem).collect(Collectors.toList());
    }

    @Override
    public void addItemToBasket(StoreItem storeItem, User user, Integer count) {
        basketRepository.save(Basket.builder()
                .user(user)
                .storeItem(storeItem)
                .count(count)
                .build()
        );
    }

    @Override
    public void removeFromBasket(User user, StoreItem storeItem) {
        StoreItem st = (StoreItem) basketRepository.getBasketByUserId(user.getId())
                .stream()
                .map(Basket::getStoreItem)
                .filter(storeItem1 -> storeItem1.equals(storeItem));


        basketRepository.delete(basketRepository.findBasketItemByStoreItem(st));
    }

    @Override
    public void clearBasket(User user) {
        //basketRepository.deleteAllByUserId(user.getId());//не пашет
    }

    @Override
    public List<StoreItem> viewAllItems() {
        return storeItemRepository.findAll().stream()
                .filter(StoreItem::isAvailable).collect(Collectors.toList());
    }

    @Override
    public void buyItems(Integer... id) {

    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }
}
