package com.daniil.courses.services;

import com.daniil.courses.dto.AddressDto;
import com.daniil.courses.dto.BasketDto;
import com.daniil.courses.dto.StoreItemDto;
import com.daniil.courses.dto.UserDto;
import com.daniil.courses.models.Address;
import com.daniil.courses.models.Order;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.models.UserOrder;
import com.daniil.courses.role_models.User;

import java.util.List;

public interface UserService {
    /**
     * получить все адреса юзера
     */
    List<AddressDto> getAllAddressesByUser(Integer userId);

    /**
     * удалить адрес
     */
    void removeAddressByUser(Integer addressId, Integer userId);

    /**
     * добавить адрес
     */
    AddressDto addAddressByUser(Integer userId, AddressDto addressdto);

    /**
     * изменить адрес
     */
    AddressDto refactorAddressByUser(AddressDto addressDto, Integer userId, Integer addressId);

    /**
     * получить корзину
     */
    List<BasketDto> getBasketByUser(Integer userId);

    /**
     * добавить товар в корзину
     */
    void addItemToBasketByUser(StoreItemDto storeItemDto, Integer userId, Integer count);

    /**
     * удалить товар из корзины
     */
    void removeFromBasketByUser(Integer storeItemId, Integer userId);

    /**
     * очистить корзину
     */
    void clearBasketByUser(Integer userId);

    /**
     * получить все доступные товары
     */
    List<StoreItemDto> viewAvailableItems();

    /**
     * купить товар
     */
    void buyItems(Integer... id);

    /**
     * получить все заказы
     */
    List<UserOrder> getAllOrdersByUser(Integer userId);

    /**
     * создание юзера
     */
    UserDto createUser(UserDto userDto);

}
