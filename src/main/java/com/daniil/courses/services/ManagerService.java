package com.daniil.courses.services;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dto.StoreItemDto;

import java.math.BigDecimal;
import java.util.List;

public interface ManagerService {

    StoreItemDto addNewItem(ItemDto itemDto, Integer managerId, BigDecimal price, boolean available);

    void setAvailable(Integer storeItemId, boolean available, Integer managerId);

    List<StoreItemDto> viewAllStoreItems();

    StoreItemDto refactorStoreItem(Integer storeItemId, ItemDto itemDto, Integer managerId, BigDecimal price, boolean available);
}
