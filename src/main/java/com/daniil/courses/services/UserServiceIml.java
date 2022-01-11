package com.daniil.courses.services;

import com.daniil.courses.exceptions.AddressIsNotFound;
import com.daniil.courses.exceptions.UserNotFound;
import com.daniil.courses.models.Address;
import com.daniil.courses.models.Basket;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.models.UserOrder;
import com.daniil.courses.repositories.*;
import com.daniil.courses.role_models.User;
import org.apache.el.stream.Stream;
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
        return addressRepository.findByUserIdAndVisible(userId, true);
    }

    public void removeAddressByUser(Integer addressId, Integer userId) {
        Address removeAddress = addressRepository.findByUserIdAndIdAndVisible(userId, addressId, true);
        if (removeAddress != null) {
            removeAddress.setVisible(false);
            addressRepository.save(removeAddress);
        } else throw new AddressIsNotFound("Address is not found");
    }

    @Override
    public List<Address> addAddressByUser(Integer userId, Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User not found"));
        user.setAddresses(List.of(address));
        addressRepository.save(address);
        return addressRepository.findAll().stream()
                .filter(address1 -> address1.getUser().getId()
                        .equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public Address refactorAddressByUser(Address address, Integer userId) {

        Address changedAddress = addressRepository.findByUserIdAndIdAndVisible(userId, address.getId(), true);

        if (changedAddress == null)
            throw new AddressIsNotFound("Address not found");

        changedAddress.setCity(address.getCity());
        changedAddress.setStreet(address.getStreet());
        changedAddress.setBase(address.getBase());
        changedAddress.setFlat(address.getFlat());
        changedAddress.setFloor(address.getFloor());
        changedAddress.setEntrance(address.getEntrance());

        return addressRepository.save(changedAddress);
    }

    @Override
    public List<StoreItem> getBasketByUser(Integer userId) {
        return basketRepository.findBasketByUserId(userId).stream()
                .map(Basket::getStoreItem).collect(Collectors.toList());
    }

    @Override
    public void addItemToBasketByUser(StoreItem storeItem, Integer userId, Integer count) {
        basketRepository.save(Basket.builder()
                .user(userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User not found")))
                .storeItem(storeItem)
                .count(count)
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
    public List<StoreItem> viewAvailableItems() {
        return storeItemRepository.findAll().stream()
                .filter(StoreItem::isAvailable).collect(Collectors.toList());
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
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
