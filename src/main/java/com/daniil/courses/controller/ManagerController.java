package com.daniil.courses.controller;

import com.daniil.courses.dal.entity.Manager;
import com.daniil.courses.dal.repositories.ManagerRepository;
import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.ManagerUserDto;
import com.daniil.courses.security.AccessAdminAndManager;
import com.daniil.courses.services.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/manager/")
@RequiredArgsConstructor
@Slf4j
public class ManagerController {
    private final ManagerService managerService;
    private final ManagerRepository managerRepository;


    @AccessAdminAndManager
    @PostMapping("storeItem")
    @Operation(description = "Добавление нового товара")
    public ManagerStoreItemDto addNewItem(@RequestBody ManagerStoreItemDto storeItemDto, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        return managerService.addNewItem(storeItemDto, manager.getId());
    }

    @AccessAdminAndManager
    @PutMapping(value = "storeItem/{storeItemId}/{available}")
    @Operation(description = "Установить доступ к товару")
    public void setAvailable(@PathVariable Integer storeItemId, @PathVariable boolean available, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        log.warn("{}", manager);
        if (manager == null)
            throw new UsernameNotFoundException("");
        managerService.setAvailable(storeItemId, available, manager.getId());
    }

    @AccessAdminAndManager
    @GetMapping("storeItems")
    @Operation(description = "Получить весь список товаров")
    public List<ManagerStoreItemDto> viewStoreItems(Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        return managerService.viewAllStoreItems();
    }

    @AccessAdminAndManager
    @PutMapping("refactorStoreItem/{storeItemId}")
    @Operation(description = "Редактирование товара")
    public ManagerStoreItemDto refactorStoreItem(@PathVariable Integer storeItemId, @RequestBody ManagerStoreItemDto storeItemDto, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        return managerService.refactorStoreItem(storeItemId, storeItemDto, manager.getId());
    }

    @AccessAdminAndManager
    @PutMapping("setStatus/{orderId}")
    @Operation(description = "Установить заказу следующий статус")
    public void setOrderStatus(@PathVariable Integer orderId, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        managerService.setOrderStatus(orderId, manager.getId());
    }

    @AccessAdminAndManager
    @GetMapping("orders/{userId}")
    @Operation(description = "Получить все заказы пользователя")
    public List<ManagerOrderDto> getAllUserOrders(@PathVariable Integer userId, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        return managerService.getAllUserOrders(userId);
    }

    @AccessAdminAndManager
    @GetMapping("allOrders")
    @Operation(description = "Получить все заказы")
    public List<ManagerOrderDto> getAllOrders(Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        return managerService.getAllOrders();
    }

    @AccessAdminAndManager
    @GetMapping("getAllUsers")
    @Operation(description = "Получить всех клиентов")
    public List<ManagerUserDto> getAllShopUsers(Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        return managerService.getAllUsers();
    }

    @AccessAdminAndManager
    @PutMapping("block/{userId}")
    @Operation(description = "Заблокировать клиента")
    public void blockUser(@PathVariable Integer userId, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        managerService.blockUser(userId);
    }

    @AccessAdminAndManager
    @PutMapping("unlock/{userId}")
    @Operation(description = "Разблокировать клиента")
    public void unLockUser(@PathVariable Integer userId, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        managerService.unlockUser(userId);
    }
}
