package com.daniil.courses.controller;

import com.daniil.courses.dto.*;
import com.daniil.courses.payment.PaymentRequest;
import com.daniil.courses.repositories.UserRepository;
import com.daniil.courses.role_models.User;
import com.daniil.courses.security.AccessUser;
import com.daniil.courses.services.BasketService;
import com.daniil.courses.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final BasketService basketService;

    @GetMapping("storeItems")
    @Operation(description = "Вывод всех доступных товаров")
    public List<UserStoreItemDto> getAvailableShopItems() {
        return userService.viewAvailableItems();
    }

    @AccessUser
    @GetMapping("addresses")
    @Operation(description = "Получить адреса юзера")
    public List<AddressDto> getAddresses(Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        return userService.getAllAddressesByUser(user.getId());
    }

    @AccessUser
    @DeleteMapping("removeAddress/{addressId}")
    @Operation(description = "Удалить адрес")
    public void removeAddresses(Principal principal, @PathVariable Integer addressId) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        userService.removeAddressByUser(addressId, user.getId());
    }

    @AccessUser
    @PostMapping("addAddress")
    @Operation(description = "Добавить адрес")
    public AddressDto addAddress(@RequestBody AddressDto addressDto, Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        return userService.addAddressByUser(user.getId(), addressDto);
    }

    @AccessUser
    @PutMapping("refactorAddress/{addressId}")
    @Operation(description = "Редактировать адрес")
    public AddressDto refactorAddress(@RequestBody AddressDto addressDto, @PathVariable Integer addressId, Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        return userService.refactorAddressByUser(addressDto, user.getId(), addressId);
    }

    @AccessUser
    @GetMapping("Basket")
    @Operation(description = "Получить корзину")
    public List<BasketDto> Basket(Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        return basketService.getBasketByUser(user.getId());
    }

    @AccessUser
    @PostMapping("{count}/addToBasket/{storeItemId}")
    @Operation(description = "Добавить товар в корзину")
    public void addToBasket(@PathVariable Integer storeItemId, @PathVariable Integer count, Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        basketService.addItemToBasketByUser(storeItemId, user.getId(), count);
    }

    @AccessUser
    @DeleteMapping("deleteFromBasket/{storeItemId}")
    @Operation(description = "Удаление товара из корзины")
    public void removeItemFromBasket(@PathVariable Integer storeItemId, Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        basketService.removeFromBasketByUser(storeItemId, user.getId());
    }

    @AccessUser
    @GetMapping("clearBasket")
    @Operation(description = "Очищение корзины")
    public void clearBasket(Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        basketService.clearBasketByUser(user.getId());
    }

    @AccessUser
    @GetMapping("byeItems/{addressId}")
    public PaymentRequest byeItems(@PathVariable Integer addressId, Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        return userService.buyItems(user.getId(), addressId);
    }

    @AccessUser
    @GetMapping("Orders")
    @Operation(description = "Получить все заказы")
    public List<UserOrderDto> getAllOrders(Principal principal) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(),true);
        if(user == null)
            throw new UsernameNotFoundException("Sorry but you are blocked :(");
        return userService.getAllOrdersByUser(user.getId());
    }

    @PostMapping("createUser")
    @Operation(description = "Создание нового юзера")
    public UserDto createUser(@RequestBody UserDto userdto) {
        return userService.createUser(userdto);
    }
}
