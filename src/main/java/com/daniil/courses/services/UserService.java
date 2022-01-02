package com.daniil.courses.services;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.Order;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.models.UserOrder;
import com.daniil.courses.role_models.User;

import java.util.List;

public interface UserService {

    List<Address> getAllAddressesByUser(Integer userId);

    void removeAddressByUser(Integer id);

    List<Address> addAddressByUser(User user, Address address);

     Address refactorAddressByUser (Address address);

    List<StoreItem> getBasketByUser(User user);

    void addItemToBasketByUser(StoreItem storeItem, User user, Integer count);

    void removeFromBasketByUser(StoreItem storeItem, User user);

    void clearBasketByUser(User user);

    List<StoreItem> viewAvailableItems();

    void buyItems(Integer... id);

    List<UserOrder> getAllOrdersByUser(User user);

}
