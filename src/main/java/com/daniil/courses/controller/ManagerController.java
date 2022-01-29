package com.daniil.courses.controller;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.ManagerUserDto;
import com.daniil.courses.repositories.ManagerRepository;
import com.daniil.courses.role_models.Manager;
import com.daniil.courses.security.AccessAdminAndManager;
import com.daniil.courses.services.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/manager/")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    private final ManagerRepository managerRepository;

    @AccessAdminAndManager
    @PostMapping("createItem")
    @Operation(description = "Добавление нового товара")
    public ManagerStoreItemDto addNewItem(@RequestBody ManagerStoreItemDto storeItemDto, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumber(principal.getName());
        return managerService.addNewItem(storeItemDto, manager.getId());
    }

    @AccessAdminAndManager
    @PutMapping(value = "setAvailable/{storeItemId}/{available}")
    @Operation(description = "Установить доступ к товару")
    public void setAvailable(@PathVariable Integer storeItemId, @PathVariable boolean available, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumber(principal.getName());
        managerService.setAvailable(storeItemId, available, manager.getId());
    }

    @AccessAdminAndManager
    @GetMapping("storeItems")
    @Operation(description = "Получить весь список товаров")
    public List<ManagerStoreItemDto> viewStoreItems() {
        return managerService.viewAllStoreItems();
    }

    @AccessAdminAndManager
    @PutMapping("refactorStoreItem/{storeItemId}")
    @Operation(description = "Редактирование товара")
    public ManagerStoreItemDto refactorStoreItem(@PathVariable Integer storeItemId, @RequestBody ManagerStoreItemDto storeItemDto, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumber(principal.getName());
        return managerService.refactorStoreItem(storeItemId, storeItemDto, manager.getId());
    }

    @AccessAdminAndManager
    @PutMapping("setStatus/{externalId}")
    @Operation(description = "Установить заказу следующий статус")
    public String setOrderStatus(@PathVariable String externalId, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumber(principal.getName());
        return managerService.setOrderStatus(externalId, manager.getId());
    }

    @AccessAdminAndManager
    @GetMapping("getUserOrders/{userId}")
    @Operation(description = "Получить все заказы пользователя")
    public List<ManagerOrderDto> getAllUserOrders(@PathVariable Integer userId) {
        return managerService.getAllUserOrders(userId);
    }

    @AccessAdminAndManager
    @GetMapping("getAllOrders")
    @Operation(description = "Получить все заказы")
    public List<ManagerOrderDto> getAllOrders() {
        return managerService.getAllOrders();
    }

    @AccessAdminAndManager
    @GetMapping("getAllUsers")
    public List<ManagerUserDto> getAllShopUsers() {
        return managerService.getAllUsers();
    }

    @AccessAdminAndManager
    @PutMapping("block/{userId}")
    public String blockUser(@PathVariable Integer userId) {
        return managerService.blockUser(userId);
    }

    @AccessAdminAndManager
    @PutMapping("unlock/{userId}")
    public String unLockUser(@PathVariable Integer userId) {
        return managerService.unlockUser(userId);
    }
}
