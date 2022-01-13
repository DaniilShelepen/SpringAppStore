package com.daniil.courses.services;

import com.daniil.courses.dto.StoreItemDto;
import com.daniil.courses.models.StoreItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ManagerService {

    StoreItemDto addNewItem(StoreItemDto storeItemDto);

    void setAvailable(StoreItem storeItem, boolean available);

    List<StoreItem> viewAllStoreItems();
}
