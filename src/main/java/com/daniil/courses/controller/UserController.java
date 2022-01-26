package com.daniil.courses.controller;

import com.daniil.courses.payment.PaymentRequest;
import com.daniil.courses.dto.*;
import com.daniil.courses.repositories.AddressRepository;
import com.daniil.courses.repositories.UserRepository;
import com.daniil.courses.role_models.User;
import com.daniil.courses.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("api/users/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("storeItems")
    @Operation(description = "Вывод всех доступных товаров")
    public List<UserStoreItemDto> getAvailableShopItems() {
        return userService.viewAvailableItems();
    }

    @GetMapping("addresses")
    @Operation(description = "Получить адреса юзера")
    public List<AddressDto> getAddresses(Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);//TODO проверь и доделай со всеми
        return userService.getAllAddressesByUser(user.getId());
    }

    @DeleteMapping("removeAddress/{addressId}")
    @Operation(description = "Удалить адрес")
    public void removeAddresses(Principal principal, @PathVariable Integer addressId) {
        User user = userRepository.findByPhoneNumber(principal.getName());
        userService.removeAddressByUser(addressId, user.getId());
    }

    @PostMapping("addAddress")
    @Operation(description = "Добавить адрес")
    public AddressDto addAddress(@RequestBody AddressDto addressDto, Principal principal) {
        User user = userRepository.findByPhoneNumber(principal.getName());
        return userService.addAddressByUser(user.getId(), addressDto);
    }

    @PutMapping("refactorAddress/{addressId}")
    @Operation(description = "Редактировать адрес")
    public AddressDto refactorAddress(@RequestBody AddressDto addressDto, @PathVariable Integer addressId, Principal principal) {
        User user = userRepository.findByPhoneNumber(principal.getName());
        return userService.refactorAddressByUser(addressDto, user.getId(), addressId);
    }

    @GetMapping("Basket")
    @Operation(description = "Получить корзину")
    public List<BasketDto> Basket(Principal principal) {
        User user = userRepository.findByPhoneNumber(principal.getName());
        return userService.getBasketByUser(user.getId());
    }

    @PostMapping("{count}/addToBasket/{storeItemId}")
    @Operation(description = "Добавить товар в корзину")
    public void addToBasket(@PathVariable Integer storeItemId, @PathVariable Integer count, Principal principal) {
        User user = userRepository.findByPhoneNumber(principal.getName());
        userService.addItemToBasketByUser(storeItemId, user.getId(), count);
    }

    @DeleteMapping("deleteFromBasket/{storeItemId}")
    @Operation(description = "Удаление товара из корзины")
    public void removeItemFromBasket(@PathVariable Integer storeItemId, Principal principal) {
        User user = userRepository.findByPhoneNumber(principal.getName());
        userService.removeFromBasketByUser(storeItemId, user.getId());
    }

    @GetMapping("clearBasket")
    @Operation(description = "Очищение корзины")
    public void clearBasket(Principal principal) {
        User user = userRepository.findByPhoneNumber(principal.getName());
        userService.clearBasketByUser(user.getId());
    }

    @GetMapping("byeItems/{addressId}")
    public PaymentRequest byeItems(@PathVariable Integer addressId, Principal principal) {
        User user = userRepository.findByPhoneNumber(principal.getName());
        return userService.buyItems(user.getId(), addressId);
    }

    @GetMapping("Orders")
    @Operation(description = "Получить все заказы")
    public List<UserOrderDto> getAllOrders(Principal principal) {
        User user = userRepository.findByPhoneNumber(principal.getName());
        return userService.getAllOrdersByUser(user.getId());
    }

    @PostMapping("createUser")
    @Operation(description = "Создание нового юзера")
    public UserDto createUser(@RequestBody UserDto userdto) {
        return userService.createUser(userdto);
    }
}
