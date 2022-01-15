package com.daniil.courses.controller;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dto.StoreItemDto;
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

    @PostMapping("{managerId}/createItem")
    @Operation(description = "Добавление нового товара")
    public StoreItemDto addNewItem(@RequestBody ItemDto itemDto, @PathVariable Integer managerId, @RequestBody BigDecimal price, @RequestBody boolean available) {
        return managerService.addNewItem(itemDto, managerId, price, available);
    }

    @PutMapping(value = "/{managerId}/setAvailable/{storeItemId}/{available}")
    @Operation(description = "Установить доступ к товару")
    public void setAvailable(@PathVariable Integer storeItemId, @PathVariable boolean available, @PathVariable Integer managerId) {
        managerService.setAvailable(storeItemId, available, managerId);
    }

    @GetMapping("storeItems")
    @Operation(description = "Получить весь список товаров")
    public List<StoreItemDto> viewStoreItems() {
        return managerService.viewAllStoreItems();
    }

    @PutMapping("{managerId}/refactorStoreItem/{storeItemId}")
    public StoreItemDto refactorStoreItem(@PathVariable Integer storeItemId, @RequestBody ItemDto itemDto, @PathVariable Integer managerId, @RequestBody BigDecimal price, @RequestBody boolean available) {
        return managerService.refactorStoreItem(storeItemId, itemDto, managerId, price, available);
    }

}
