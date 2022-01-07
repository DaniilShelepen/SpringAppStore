package com.daniil.courses.services;

import com.daniil.courses.exceptions.StoreItemIsNotFound;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.ItemRepository;
import com.daniil.courses.repositories.StoreItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceIml implements ManagerService {

    ItemRepository itemRepository;
    StoreItemRepository storeItemRepository;

    @Autowired
    public ManagerServiceIml(ItemRepository itemRepository, StoreItemRepository storeItemRepository) {
        this.itemRepository = itemRepository;
        this.storeItemRepository = storeItemRepository;
    }

    @Override
    public StoreItem addNewItem(String name, String description, String type, String driverConfiguration,
                                String CPU, Date releaseDate, BigDecimal price, boolean available) {

        Item Item = com.daniil.courses.models.Item.builder()
                .name(name)
                .description(description)
                .type(type)
                .driverConfiguration(driverConfiguration)
                .CPU(CPU)
                .releaseDate(releaseDate)
                .build();

        itemRepository.save(Item);


        StoreItem StoreItem = com.daniil.courses.models.StoreItem.builder()
                .price(price)
                .available(available)
                .item(Item)
                .build();

        storeItemRepository.save(StoreItem);

        return StoreItem;

    }

    @Override
    public void setAvailable(StoreItem storeItem, boolean available) {
        StoreItem storeItem1 = storeItemRepository.findById(storeItem.getId()).orElseThrow(() -> new StoreItemIsNotFound("Store item is not found"));
        storeItem1.setAvailable(available);
        storeItemRepository.save(storeItem1);
    }

    @Override
    public List<StoreItem> viewAllStoreItems() {
        return storeItemRepository.findAll();
    }
}
