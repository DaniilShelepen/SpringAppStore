package com.daniil.courses.services;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.Order;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.role_models.User;

import java.util.List;

public interface UserService {

    List<Address> getAllUserAddresses(Integer userId);

    void removeAddress(Integer id);

    List<Address> addAddress(User user, Address address);

     Address refactorAddress (Address address);

    List<Item> getBasket();

    void addItemToBasket(Integer count);

    void removeFromBasket(Integer id);

    void clearBasket();

    List<StoreItem> viewAllItems();

    void buyItems(Integer... id);

    List<Order> getAllOrders();

}
