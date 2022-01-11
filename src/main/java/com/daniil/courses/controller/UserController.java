package com.daniil.courses.controller;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.models.UserOrder;
import com.daniil.courses.role_models.User;
import com.daniil.courses.services.FilterService;
import com.daniil.courses.services.ManagerService;
import com.daniil.courses.services.UserService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/users/")
@RequiredArgsConstructor
public class UserController {
    private final FilterService filterService;
    private final ManagerService managerService;
    private final UserService userService;


    @GetMapping("storeItems")
    @Operation(description = "Вывод всех доступных товаров")
    public List<StoreItem> getAvailableShopItems() {
        return userService.viewAvailableItems();
    }

    @GetMapping("{userId}/addresses")
    @Operation(description = "Получить адреса юзера")
    public List<Address> getAddresses(@PathVariable Integer userId) {
        return userService.getAllAddressesByUser(userId);
    }

    @DeleteMapping("{userId}/removeAddress/{addressId}")//"{userId}/removeAddress/{address}"
    @Operation(description = "Удалить адрес")
    public void removeAddresses(@PathVariable Integer userId, @PathVariable Integer addressId) {
        userService.removeAddressByUser(addressId, userId);
    }

    @PostMapping("{userId}/addAddress")
    @Operation(description = "Добавить адрес")
    public List<Address> addAddress(@RequestBody Address address, @PathVariable Integer userId) {
        return userService.addAddressByUser(userId, address);
    }

    @PutMapping("{userId}/refactorAddress")
    @Operation(description = "Редактировать адрес")
    public Address refactorAddress(@RequestBody Address address, @PathVariable Integer userId) {
        return userService.refactorAddressByUser(address, userId);
    }

    @GetMapping("{userId}/Basket")
    @Operation(description = "Получить корзину")
    public List<StoreItem> Basket(@PathVariable Integer userId) {
        return userService.getBasketByUser(userId);
    }

    @PostMapping("{userId}/{count}/addToBasket")
    @Operation(description = "Добавить товар в корзину")
    public void addToBasket(@RequestBody StoreItem storeItem, @PathVariable Integer count, @PathVariable Integer userId) {
        userService.addItemToBasketByUser(storeItem, userId, count);
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

    @GetMapping("{userId}/Orders")
    @Operation(description = "Получить все заказы")
    public List<UserOrder> getAllOrders(@PathVariable Integer userId) {
        return userService.getAllOrdersByUser(userId);
    }


    @PostMapping("createUser")
    @Operation(description = "Создание нового юзера")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
