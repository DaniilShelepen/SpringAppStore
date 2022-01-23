package com.daniil.courses.services;

import com.daniil.courses.bankApi.PaymentRequest;
import com.daniil.courses.dto.*;
import com.daniil.courses.dto.UserOrderDto;

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
    void addItemToBasketByUser(Integer storeItemId, Integer userId, Integer count);

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
    List<UserStoreItemDto> viewAvailableItems();

    /**
     * купить товар
     */
    PaymentRequest buyItems(Integer userId, Integer addressId);

    /**
     * получить все заказы
     */
    List<UserOrderDto> getAllOrdersByUser(Integer userId);

    /**
     * создание юзера
     */
    UserDto createUser(UserDto userDto);

}
