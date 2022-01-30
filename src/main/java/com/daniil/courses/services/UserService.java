package com.daniil.courses.services;

import com.daniil.courses.payment.PaymentRequest;
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
     * получить все доступные товары
     */
    List<UserStoreItemDto> viewAvailableItems();

    /**
     * купить товар
     */
    PaymentRequest buyItems(Integer userId, Integer addressId, String accountId);

    /**
     * получить все заказы
     */
    List<UserOrderDto> getAllOrdersByUser(Integer userId);

    /**
     * создание юзера
     */
    UserDto createUser(UserDto userDto);

}
