package com.daniil.courses.services.impl;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dto.StoreItemDto;
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
    public StoreItemDto addNewItem(ItemDto ItemDto, Integer managerId, BigDecimal price, boolean available) {

        Item item = Item.builder()
                .name(ItemDto.getName())
                .description(ItemDto.getDescription())
                .type(ItemDto.getType())
                .driverConfiguration(ItemDto.getDriverConfiguration())
                .CPU(ItemDto.getCPU())
                .releaseDate(ItemDto.getReleaseDate())
                .build();

        StoreItem storeItem = StoreItem.builder()
                .price(price)
                .available(available)
                .item(item)
                .manager(managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager is not found")))
                .build();

        itemRepository.save(item);
        storeItemRepository.save(storeItem);

//        return  storeItemRepository.findById(storeItem.getId()).stream()
//                .map(storeItem1 -> new StoreItemDto(storeItem1.getItem(), storeItem1.getPrice())).findFirst().orElseThrow();//это не баг, это фича

        return StoreItemDto.builder()
                .item(item)
                .price(storeItem.getPrice())
                .available(storeItem.isAvailable())
                .build();
    }

    @Override
    public void setAvailable(Integer storeItemId, boolean available, Integer managerId) {
        StoreItem storeItem1 = storeItemRepository.findById(storeItemId).orElseThrow(() -> new StoreItemIsNotFound("Store item is not found"));
        storeItem1.setAvailable(available);
        storeItem1.setManager(managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager is not found")));
        storeItemRepository.save(storeItem1);
    }

    @Override
    public List<StoreItemDto> viewAllStoreItems() {
        return storeItemRepository.findAll().stream()
                .map(storeItem -> new StoreItemDto(storeItem.getItem(), storeItem.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public StoreItemDto refactorStoreItem(Integer storeItemId, ItemDto itemDto, Integer managerId, BigDecimal price, boolean available) {

        StoreItem refactorStoreItem = storeItemRepository.findById(storeItemId).orElseThrow(() -> new StoreItemIsNotFound("Store item is not found"));

//        Item item = Item.builder()
//                .name(itemDto.getName())
//                .description(itemDto.getDescription())
//                .type(itemDto.getType())
//                .driverConfiguration(itemDto.getDriverConfiguration())
//                .CPU(itemDto.getCPU())
//                .releaseDate(itemDto.getReleaseDate())
//                .build();

        refactorStoreItem.setItem(Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .type(itemDto.getType())
                .driverConfiguration(itemDto.getDriverConfiguration())
                .CPU(itemDto.getCPU())
                .releaseDate(itemDto.getReleaseDate())
                .build());
        refactorStoreItem.setPrice(price);
        refactorStoreItem.setAvailable(available);
        refactorStoreItem.setManager(managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager is not found")));

        Item findItem = itemRepository.findById(refactorStoreItem.getItem().getId()).orElseThrow();
        findItem.setCPU(itemDto.getCPU());//и тд
        //itemRepository.save(findItem);// refactorStoreItem.getItem().getId()//TODO тут поиграй надо сейвить этот айтем или он сам гений(типо по айди найди и засейви айтем)
        storeItemRepository.save(refactorStoreItem);

        return storeItemRepository.findById(refactorStoreItem.getId()).stream()
                .map(storeItem1 -> new StoreItemDto(storeItem1.getItem(), storeItem1.getPrice())).findFirst().orElseThrow();
    }
}
