package com.daniil.courses.services;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.Order;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.models.UserOrder;
import com.daniil.courses.role_models.User;

import java.util.List;

public interface UserService {
    /** получить все адреса юзера */
    List<Address> getAllAddressesByUser(Integer userId);

    /** удалить адрес */
    void removeAddressByUser(Address address);

    /** добавить адрес */
    List<Address> addAddressByUser(User user, Address address);

    /** изменить адрес */
     Address refactorAddressByUser (Address address);

    /** получить корзину */
    List<StoreItem> getBasketByUser(User user);

    /** добавить товар в корзину */
    void addItemToBasketByUser(StoreItem storeItem, User user, Integer count);

    /** удалить товар из корзины */
    void removeFromBasketByUser(StoreItem storeItem, User user);

    /** очистить корзину */
    void clearBasketByUser(User user);

    /** получить все доступные товары */
    List<StoreItem> viewAvailableItems();

    /** купить товар */
    void buyItems(Integer... id);

    /** получить все заказы */
    List<UserOrder> getAllOrdersByUser(User user);

}
