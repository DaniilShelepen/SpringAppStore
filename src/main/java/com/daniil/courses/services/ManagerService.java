package com.daniil.courses.services;

import com.daniil.courses.models.StoreItem;

import java.util.List;

public interface ManagerService {
    StoreItem addNewItem();

    void setAvailable(Integer storeItem_id);

    List<StoreItem> viewAllStoreItems();
}
