package com.daniil.courses.services.impl;

import com.daniil.courses.dto.StoreItemDto;
import com.daniil.courses.exceptions.StoreItemIsNotFound;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.ItemRepository;
import com.daniil.courses.repositories.StoreItemRepository;
import com.daniil.courses.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    ItemRepository itemRepository;
    StoreItemRepository storeItemRepository;

    @Autowired
    public ManagerServiceImpl(ItemRepository itemRepository, StoreItemRepository storeItemRepository) {
        this.itemRepository = itemRepository;
        this.storeItemRepository = storeItemRepository;
    }

    @Override
    public StoreItemDto addNewItem(StoreItemDto storeItemDto) {

        Item Item = com.daniil.courses.models.Item.builder()
                .name(storeItemDto.getItem().getName())
                .description(storeItemDto.getItem().getDescription())
                .type(storeItemDto.getItem().getType())
                .driverConfiguration(storeItemDto.getItem().getDriverConfiguration())
                .CPU(storeItemDto.getItem().getCPU())
                .releaseDate(storeItemDto.getItem().getReleaseDate())
                .build();

        itemRepository.save(Item);


        StoreItem StoreItem = com.daniil.courses.models.StoreItem.builder()
                .price(storeItemDto.getPrice())
                .available(storeItemDto.isAvailable())
                .item(Item)
                .build();

        storeItemRepository.save(StoreItem);

        return storeItemDto;

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
