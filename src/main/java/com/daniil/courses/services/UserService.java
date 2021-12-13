package com.daniil.courses.services;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.Order;
import com.daniil.courses.role_models.User;

import java.util.List;

public interface UserService {

    List<Address> getAllAddresses();

    List<Address> removeAddress(Integer id);

    void addAddress(User user,Address address);

    List<Address> refactorAddress(Integer id);

    List<Item> getBasket();

    void addItemToBasket(Integer count);

    void removeFromBasket(Integer id);

    void clearBasket();

    List<Item> viewAllItems();

    void buyItems(Integer... id);

    List<Order> getAllOrders();

}
