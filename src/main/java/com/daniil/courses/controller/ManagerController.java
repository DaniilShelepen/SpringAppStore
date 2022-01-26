package com.daniil.courses.controller;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.ManagerUserDto;
import com.daniil.courses.services.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/manager/")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("{managerId}/createItem/{price}/{available}")
    @Operation(description = "Добавление нового товара")
    public ManagerStoreItemDto addNewItem(@PathVariable Integer managerId, @RequestBody ItemDto itemDto, @PathVariable BigDecimal price, @PathVariable boolean available) {
        return managerService.addNewItem(itemDto, managerId, price, available);
    }

    @PutMapping(value = "/{managerId}/setAvailable/{storeItemId}/{available}")
    @Operation(description = "Установить доступ к товару")
    public void setAvailable(@PathVariable Integer storeItemId, @PathVariable boolean available, @PathVariable Integer managerId) {
        managerService.setAvailable(storeItemId, available, managerId);
    }

    @GetMapping("storeItems")
    @Operation(description = "Получить весь список товаров")
    public List<ManagerStoreItemDto> viewStoreItems() {
        return managerService.viewAllStoreItems();
    }

    @PutMapping("{managerId}/refactorStoreItem/{storeItemId}/{price}/{available}")
    @Operation(description = "Редактирование товара")
    public ManagerStoreItemDto refactorStoreItem(@PathVariable Integer storeItemId, @RequestBody ItemDto itemDto, @PathVariable Integer managerId, @PathVariable BigDecimal price, @PathVariable boolean available) {
        return managerService.refactorStoreItem(storeItemId, itemDto, managerId, price, available);
    }

    @PutMapping("{managerId}/setStatus/{externalId}")
    @Operation(description = "Установить заказу следующий статус")
    public String setOrderStatus(@PathVariable String externalId, @PathVariable Integer managerId) {
        return managerService.setOrderStatus(externalId, managerId);
    }

    @GetMapping("getUserOrders/{userId}")
    @Operation(description = "Получить все заказы пользователя")
    public List<ManagerOrderDto> getAllUserOrders(@PathVariable Integer userId) {
        return managerService.getAllUserOrders(userId);
    }

    @GetMapping("getAllOrders")
    @Operation(description = "Получить все заказы")
    public List<ManagerOrderDto> getAllOrders() {
        return managerService.getAllOrders();
    }


    @GetMapping("getAllUsers")
    public List<ManagerUserDto> getAllShopUsers() {
        return managerService.getAllUsers();
    }

    @PutMapping("block/{userId}")
    public String blockUser(@PathVariable Integer userId) {
        return managerService.blockUser(userId);
    }

    @PutMapping("unlock/{userId}")
    public String unLockUser(@PathVariable Integer userId) {
        return managerService.unlockUser(userId);
    }
}
