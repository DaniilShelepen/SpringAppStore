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
    void removeAddressByUser(Integer addressId, Integer userId);

    /** добавить адрес */
    List<Address> addAddressByUser(Integer userId, Address address);

    /** изменить адрес */
     Address refactorAddressByUser (Address address,Integer userId);

    /** получить корзину */
    List<StoreItem> getBasketByUser(Integer userId);

    /** добавить товар в корзину */
    void addItemToBasketByUser(StoreItem storeItem, Integer userId, Integer count);

    /** удалить товар из корзины */
    void removeFromBasketByUser(Integer storeItemId, Integer userId);

    /** очистить корзину */
    void clearBasketByUser(Integer userId);

    /** получить все доступные товары */
    List<StoreItem> viewAvailableItems();

    /** купить товар */
    void buyItems(Integer... id);

    /** получить все заказы */
    List<UserOrder> getAllOrdersByUser(Integer userId);

    /** создание юзера */
    User createUser(User user);

}
