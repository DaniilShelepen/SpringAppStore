package com.daniil.courses.services;

import com.daniil.courses.models.*;
import com.daniil.courses.repositories.*;
import com.daniil.courses.role_models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceIml implements UserService {


    UserRepository userRepository;
    AddressRepository addressRepository;
    StoreItemRepository storeItemRepository;
    BasketRepository basketRepository;
    OrderRepository orderRepository;

    @Autowired
    public UserServiceIml(UserRepository userRepository, AddressRepository addressRepository,
                          StoreItemRepository storeItemRepository, BasketRepository basketRepository,
                          OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.storeItemRepository = storeItemRepository;
        this.basketRepository = basketRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Address> getAllAddressesByUser(Integer userId) {
        return addressRepository.getAddressByUserId(userId);
    }

    public void removeAddressByUser(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public List<Address> addAddressByUser(User user, Address address) {
        user.setAddresses(List.of(address));
        addressRepository.save(address);
        return addressRepository.findAll().stream()
                .filter(address1 -> address1.getUser().getId()
                        .equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Address refactorAddressByUser(Address address) {

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
    public List<StoreItem> getBasketByUser(User user) {
        return basketRepository.findBasketByUserId(user.getId()).stream()
                .map(Basket::getStoreItem).collect(Collectors.toList());
    }

    @Override
    public void addItemToBasketByUser(StoreItem storeItem, User user, Integer count) {
        basketRepository.save(Basket.builder()
                .user(user)
                .storeItem(storeItem)
                .count(count)
                .build()
        );
    }

    @Override
    public void removeFromBasketByUser(StoreItem storeItem, User user) {
        basketRepository.deleteByStoreItemIdAndUserId(storeItem.getId(), user.getId());
    }

    @Override
    public void clearBasketByUser(User user) {
        basketRepository.deleteAllByUserId(user.getId());
    }

    @Override
    public List<StoreItem> viewAvailableItems() {
        return storeItemRepository.findAll().stream()
                .filter(StoreItem::isAvailable).collect(Collectors.toList());
    }

    @Override
    public void buyItems(Integer... id) {

    }

    @Override
    public List<UserOrder> getAllOrdersByUser(User user) {
        return orderRepository.findAllByUserId(user.getId()).stream()
                .map(order -> new UserOrder(order.getStatus(),order.getDate(),order.getDateOfRefactoring()))
                .collect(Collectors.toList());
    }
}
