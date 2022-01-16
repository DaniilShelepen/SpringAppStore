package com.daniil.courses.services.impl;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.exceptions.ManagerNotFound;
import com.daniil.courses.exceptions.StoreItemIsNotFound;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.ItemRepository;
import com.daniil.courses.repositories.ManagerRepository;
import com.daniil.courses.repositories.StoreItemRepository;
import com.daniil.courses.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService {

    ItemRepository itemRepository;
    ManagerRepository managerRepository;
    StoreItemRepository storeItemRepository;

    @Autowired
    public ManagerServiceImpl(ItemRepository itemRepository, StoreItemRepository storeItemRepository, ManagerRepository managerRepository) {
        this.itemRepository = itemRepository;
        this.storeItemRepository = storeItemRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public ManagerStoreItemDto addNewItem(ItemDto itemDto, Integer managerId, BigDecimal price, boolean available) {

        Item item = Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .type(itemDto.getType())
                .driverConfiguration(itemDto.getDriverConfiguration())
                .CPU(itemDto.getCPU())
                .releaseDate(itemDto.getReleaseDate())
                .build();

        StoreItem storeItem = StoreItem.builder()
                .price(price)
                .available(available)
                .item(item)
                .manager(managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager is not found")))
                .build();

        itemRepository.save(item);
        storeItemRepository.save(storeItem);

        return storeItemRepository.findById(storeItem.getId()).stream()
                .map(storeItem1 -> new ManagerStoreItemDto(itemDto, storeItem1.getPrice(), storeItem1.isAvailable())).findFirst().orElseThrow();

    }

    @Override
    public void setAvailable(Integer storeItemId, boolean available, Integer managerId) {
        StoreItem storeItem1 = storeItemRepository.findById(storeItemId).orElseThrow(() -> new StoreItemIsNotFound("Store item is not found"));
        storeItem1.setAvailable(available);
        storeItem1.setManager(managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager is not found")));
        storeItemRepository.save(storeItem1);
    }

    @Override
    public List<ManagerStoreItemDto> viewAllStoreItems() {
        return storeItemRepository.findAll().stream()
                .map(storeItem -> new ManagerStoreItemDto(ItemDto.builder()
                        .name(storeItem.getItem().getName())
                        .description(storeItem.getItem().getDescription())
                        .type(storeItem.getItem().getType())
                        .driverConfiguration(storeItem.getItem().getDriverConfiguration())
                        .CPU(storeItem.getItem().getCPU())
                        .releaseDate(storeItem.getItem().getReleaseDate())
                        .build(),
                        storeItem.getPrice(),
                        storeItem.isAvailable()))
                .collect(Collectors.toList());
    }

    @Override
    public ManagerStoreItemDto refactorStoreItem(Integer storeItemId, ItemDto itemDto, Integer managerId, BigDecimal price, boolean available) {

        StoreItem refactorStoreItem = storeItemRepository.findById(storeItemId).orElseThrow(() -> new StoreItemIsNotFound("Store item is not found"));
        managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager is not found"));
//        Item item = Item.builder()
//                .name(itemDto.getName())
//                .description(itemDto.getDescription())
//                .type(itemDto.getType())
//                .driverConfiguration(itemDto.getDriverConfiguration())
//                .CPU(itemDto.getCPU())
//                .releaseDate(itemDto.getReleaseDate())
//                .build();

        Item findItem = itemRepository.findById(storeItemRepository.findById(storeItemId).get().getItem().getId()).orElseThrow();

        findItem.setCPU(itemDto.getCPU());
        findItem.setType(itemDto.getType());
        findItem.setReleaseDate(itemDto.getReleaseDate());
        findItem.setDescription(itemDto.getDescription());
        findItem.setDriverConfiguration(itemDto.getDriverConfiguration());
        findItem.setName(itemDto.getName());

        itemRepository.save(findItem);

        refactorStoreItem.setItem(findItem);
        refactorStoreItem.setPrice(price);
        refactorStoreItem.setAvailable(available);
        refactorStoreItem.setManager(managerRepository.findById(managerId).orElseThrow());


        storeItemRepository.save(refactorStoreItem);

        return storeItemRepository.findById(refactorStoreItem.getId()).stream()
                .map(storeItem1 -> new ManagerStoreItemDto(itemDto, storeItem1.getPrice(),storeItem1.isAvailable())).findFirst().orElseThrow();
    }
}
