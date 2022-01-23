package com.daniil.courses.services;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.ManagerStoreItemDto;

import java.math.BigDecimal;
import java.util.List;

public interface ManagerService {

    ManagerStoreItemDto addNewItem(ItemDto itemDto, Integer managerId, BigDecimal price, boolean available);

    void setAvailable(Integer storeItemId, boolean available, Integer managerId);

    List<ManagerStoreItemDto> viewAllStoreItems();

    ManagerStoreItemDto refactorStoreItem(Integer storeItemId, ItemDto itemDto, Integer managerId, BigDecimal price, boolean available);
    List<ManagerOrderDto> getAllUserOrders(Integer userId);
}
