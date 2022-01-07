package com.daniil.courses.services;

import com.daniil.courses.models.StoreItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ManagerService {

    StoreItem addNewItem(String name, String description, String type, String driverConfiguration,
                         String CPU, Date releaseDate, BigDecimal price,boolean available);

    void setAvailable(StoreItem storeItem, boolean available);

    List<StoreItem> viewAllStoreItems();
}
