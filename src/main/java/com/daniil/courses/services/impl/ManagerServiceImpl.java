package com.daniil.courses.services.impl;

import com.daniil.courses.dto.*;
import com.daniil.courses.exceptions.ManagerNotFound;
import com.daniil.courses.exceptions.StoreItemIsNotFound;
import com.daniil.courses.exceptions.UserNotFound;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.*;
import com.daniil.courses.role_models.User;
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
    OrderRepository orderRepository;
    UserRepository userRepository;

    @Autowired
    public ManagerServiceImpl(ItemRepository itemRepository, UserRepository userRepository, StoreItemRepository storeItemRepository, ManagerRepository managerRepository, OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.storeItemRepository = storeItemRepository;
        this.managerRepository = managerRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
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
                .map(storeItem -> new ManagerStoreItemDto(ItemDto.toItemDto(storeItem.getItem()),
                        storeItem.getPrice(),
                        storeItem.isAvailable()))
                .collect(Collectors.toList());
    }

    @Override
    public ManagerStoreItemDto refactorStoreItem(Integer storeItemId, ItemDto itemDto, Integer managerId, BigDecimal price, boolean available) {

        StoreItem refactorStoreItem = storeItemRepository.findById(storeItemId).orElseThrow(() -> new StoreItemIsNotFound("Store item is not found"));
        managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager is not found"));

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
                .map(storeItem1 -> new ManagerStoreItemDto(itemDto, storeItem1.getPrice(), storeItem1.isAvailable())).findFirst().orElseThrow();
    }

    @Override
    public List<ManagerOrderDto> getAllUserOrders(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
        return orderRepository.findAllByUser(user).stream()
                .map(order -> new ManagerOrderDto(order.getStatus(), order.getDate(), order.getDateOfRefactoring(), order.getPrice(),
                        UserDto.toUserDto(order.getUser()), AddressDto.toAddressDto(order.getAddress()),order.getExternalId(),
                        order.getStoreItem().stream()
                                .map(storeItem -> storeItem.getItem().getName()).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
