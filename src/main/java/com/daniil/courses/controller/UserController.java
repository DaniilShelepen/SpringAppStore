package com.daniil.courses.controller;

import com.daniil.courses.bankApi.PaymentRequest;
import com.daniil.courses.dto.*;
import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.services.UserService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import javax.persistence.criteria.CriteriaBuilder;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("storeItems")
    @Operation(description = "Вывод всех доступных товаров")
    public List<UserStoreItemDto> getAvailableShopItems() {
        return userService.viewAvailableItems();
    }

    @GetMapping("{userId}/addresses")
    @Operation(description = "Получить адреса юзера")
    public List<AddressDto> getAddresses(@PathVariable Integer userId) {
        return userService.getAllAddressesByUser(userId);
    }

    @DeleteMapping("{userId}/removeAddress/{addressId}")//"{userId}/removeAddress/{address}"
    @Operation(description = "Удалить адрес")
    public void removeAddresses(@PathVariable Integer userId, @PathVariable Integer addressId) {
        userService.removeAddressByUser(addressId, userId);
    }

    @PostMapping("{userId}/addAddress")
    @Operation(description = "Добавить адрес")
    public AddressDto addAddress(@RequestBody AddressDto addressDto, @PathVariable Integer userId) {
        return userService.addAddressByUser(userId, addressDto);
    }

    @PutMapping("{userId}/refactorAddress/{addressId}")
    @Operation(description = "Редактировать адрес")
    public AddressDto refactorAddress(@RequestBody AddressDto addressDto, @PathVariable Integer userId, @PathVariable  Integer addressId) {
        return userService.refactorAddressByUser(addressDto, userId, addressId);
    }

    @GetMapping("{userId}/Basket")
    @Operation(description = "Получить корзину")
    public List<BasketDto> Basket(@PathVariable Integer userId) {
        return userService.getBasketByUser(userId);
    }

    @PostMapping("{userId}/{count}/addToBasket/{storeItemId}")
    @Operation(description = "Добавить товар в корзину")
    public void addToBasket(@PathVariable Integer storeItemId, @PathVariable Integer count, @PathVariable Integer userId) {
        userService.addItemToBasketByUser(storeItemId, userId, count);
    }

    @DeleteMapping("{userId}/{storeItemId}/refactorAddress")
    @Operation(description = "Удаление товара из корзины")
    public void removeItemFromBasket(@PathVariable Integer userId, @PathVariable Integer storeItemId) {
        userService.removeFromBasketByUser(storeItemId, userId);
    }

    @GetMapping("{userId}/clearBasket")
    @Operation(description = "Очищение корзины")
    public void clearBasket(@PathVariable Integer userId) {
        userService.clearBasketByUser(userId);
    }

//    /** купить товар */
//    void buyItems(Integer... id);//TODO

    @GetMapping("{userId}/byeItems/{addressId}")
    public PaymentRequest byeItems(@PathVariable Integer addressId, @PathVariable Integer userId){
        return userService.buyItems(userId,addressId);
    }


    @GetMapping("{userId}/Orders")
    @Operation(description = "Получить все заказы")
    public List<UserOrderDto> getAllOrders(@PathVariable Integer userId) {
        return userService.getAllOrdersByUser(userId);
    }


    @PostMapping("createUser")
    @Operation(description = "Создание нового юзера")
    public UserDto createUser(@RequestBody UserDto userdto) {
        return userService.createUser(userdto);
    }
}
