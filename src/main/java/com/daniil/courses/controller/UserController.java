package com.daniil.courses.controller;

import com.daniil.courses.dto.*;
import com.daniil.courses.exceptions.UserNotFound;
import com.daniil.courses.dal.repositories.UserRepository;
import com.daniil.courses.dal.entity.User;
import com.daniil.courses.security.AccessUser;
import com.daniil.courses.services.BasketService;
import com.daniil.courses.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("{me}/addresses")
    @Operation(description = "Получить адреса юзера")
    public List<AddressDto> getAddresses(Principal principal, @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        return userService.getAllAddressesByUser(user.getId());
    }

    @AccessUser
    @DeleteMapping("{me}/address/{addressId}")
    @Operation(description = "Удалить адрес")
    public void removeAddresses(Principal principal, @PathVariable Integer addressId, @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        userService.removeAddressByUser(addressId, user.getId());
    }

    @AccessUser
    @PostMapping("{me}/addAddress")
    @Operation(description = "Добавить адрес")
    public AddressDto addAddress(@RequestBody AddressDto addressDto, Principal principal, @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        return userService.addAddressByUser(user.getId(), addressDto);
    }

    @AccessUser
    @PutMapping("{me}/address/{addressId}")
    @Operation(description = "Редактировать адрес")
    public AddressDto refactorAddress(@RequestBody AddressDto addressDto, @PathVariable Integer addressId,
                                      Principal principal, @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        return userService.refactorAddressByUser(addressDto, user.getId(), addressId);
    }

    @AccessUser
    @GetMapping("{me}/basket")
    @Operation(description = "Получить корзину")
    public List<BasketDto> Basket(Principal principal, @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        return basketService.getBasketByUser(user.getId());
    }

    @AccessUser
    @PostMapping("{me}/addToBasket/{count}/{storeItemId}")
    @Operation(description = "Добавить товар в корзину")
    public void addToBasket(@PathVariable Integer storeItemId, @PathVariable Integer count, Principal principal,
                            @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        basketService.addItemToBasketByUser(storeItemId, user.getId(), count);
    }

    @AccessUser
    @DeleteMapping("{me}/deleteItem/{storeItemId}")
    @Operation(description = "Удаление товара из корзины")
    public void removeItemFromBasket(@PathVariable Integer storeItemId, Principal principal, @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        basketService.removeFromBasketByUser(storeItemId, user.getId());
    }

    @AccessUser
    @GetMapping("{me}/clearBasket")
    @Operation(description = "Очищение корзины")
    public void clearBasket(Principal principal, @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        basketService.clearBasketByUser(user.getId());
    }

    @AccessUser
    @GetMapping("{me}/buyItems/{addressId}/{bankCard}")
    public CreateOrderResponse buyItems(@PathVariable Integer addressId, Principal principal, @PathVariable String bankCard,
                                        @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        return userService.buyItems(user.getId(), addressId, bankCard);
    }

    @AccessUser
    @GetMapping("{me}/orders")
    @Operation(description = "Получить все заказы")
    public List<UserOrderDto> getAllOrders(Principal principal, @PathVariable Integer me) {
        User user = userRepository.findByPhoneNumberAndAvailable(principal.getName(), true);
        if (user == null || !user.getId().equals(me))
            throw new UserNotFound("You are blocked or 'me' not equals userId :(");
        return userService.getAllOrdersByUser(user.getId());
    }

    @PostMapping("createUser")
    @Operation(description = "Создание нового юзера")
    public UserDto createUser(@RequestBody UserDto userdto) {
        return userService.createUser(userdto);
    }
}
