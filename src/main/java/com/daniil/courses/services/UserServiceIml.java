package com.daniil.courses.services;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.Order;
import com.daniil.courses.repositories.UserRepository;
import com.daniil.courses.role_models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService {


    UserRepository userRepository;

    @Autowired
    public UserServiceIml(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Address> getAllAddresses() {
        return null;
    }

    @Override
    public List<Address> removeAddress(Integer id) {
        return null;
    }

    @Override
    public void addAddress(User user, Address address) {
        user.setAddresses(List.of(address));
    }

    @Override
    public List<Address> refactorAddress(Integer id) {
        return null;
    }

    @Override
    public List<Item> getBasket() {
        return null;
    }

    @Override
    public void addItemToBasket(Integer count) {

    }

    @Override
    public void removeFromBasket(Integer id) {

    }

    @Override
    public void clearBasket() {

    }

    @Override
    public List<Item> viewAllItems() {
        return null;
    }

    @Override
    public void buyItems(Integer... id) {

    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }
}
