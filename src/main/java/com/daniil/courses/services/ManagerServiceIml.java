package com.daniil.courses.services;

import com.daniil.courses.models.AppStore;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.AppStoreRepository;
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
    AppStoreRepository appStoreRepository;

    @Autowired
    public ManagerServiceIml(ItemRepository itemRepository, StoreItemRepository storeItemRepository, AppStoreRepository appStoreRepository) {
        this.itemRepository = itemRepository;
        this.storeItemRepository = storeItemRepository;
        this.appStoreRepository = appStoreRepository;
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


        appStoreRepository.save(AppStore.builder().build());//вот тут типо создам если пусто

        StoreItem StoreItem = com.daniil.courses.models.StoreItem.builder()
                .price(price)
                .available(available)
                .item(Item)
                .appStore(appStoreRepository.getById(1))//и вот тяну из бд
                .build();

        storeItemRepository.save(StoreItem);

        return StoreItem;

    }

    @Override
    public void setAvailable(Integer storeItem_id) {

    }

    @Override
    public List<StoreItem> viewAllStoreItems() {
        return null;
    }
}
