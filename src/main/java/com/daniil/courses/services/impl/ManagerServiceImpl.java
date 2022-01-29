package com.daniil.courses.services.impl;

import com.daniil.courses.dto.*;
import com.daniil.courses.exceptions.ManagerNotFound;
import com.daniil.courses.exceptions.StoreItemIsNotFound;
import com.daniil.courses.exceptions.UserNotFound;
import com.daniil.courses.models.Item;
import com.daniil.courses.models.Order;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.*;
import com.daniil.courses.role_models.Manager;
import com.daniil.courses.role_models.User;
import com.daniil.courses.security.Roles;
import com.daniil.courses.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService, UserDetailsService {

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
    public ManagerStoreItemDto addNewItem(ManagerStoreItemDto storeItemDto, Integer managerId) {

        Item item = Item.builder()
                .name(storeItemDto.getItemDto().getName())
                .description(storeItemDto.getItemDto().getDescription())
                .type(storeItemDto.getItemDto().getType())
                .driverConfiguration(storeItemDto.getItemDto().getDriverConfiguration())
                .CPU(storeItemDto.getItemDto().getCPU())
                .releaseDate(storeItemDto.getItemDto().getReleaseDate())
                .available(storeItemDto.isAvailable())
                .build();

        StoreItem storeItem = StoreItem.builder()
                .price(storeItemDto.getPrice())
                .available(storeItemDto.isAvailable())
                .item(item)
                .manager(managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager is not found")))
                .build();

        itemRepository.save(item);
        storeItemRepository.save(storeItem);

        return storeItemRepository.findById(storeItem.getId()).stream()
                .map(storeItem1 -> new ManagerStoreItemDto(storeItem1.getId(), storeItemDto.getItemDto(), storeItem1.getPrice(), storeItem1.isAvailable(), ManagerDto.toShortManagerDto(storeItem1.getManager()))).findFirst().orElseThrow();

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
                .map(storeItem -> new ManagerStoreItemDto(storeItem.getId(), ItemDto.toItemDto(storeItem.getItem()),
                        storeItem.getPrice(),
                        storeItem.isAvailable(),
                        ManagerDto.toShortManagerDto(storeItem.getManager())))
                .collect(Collectors.toList());
    }

    @Override
    public ManagerStoreItemDto refactorStoreItem(Integer storeItemId, ManagerStoreItemDto storeItemDto, Integer managerId) {

        StoreItem refactorStoreItem = storeItemRepository.findById(storeItemId).orElseThrow(() -> new StoreItemIsNotFound("Store item is not found"));
        refactorStoreItem.setAvailable(false);
        Item findItem = itemRepository.findById(storeItemRepository.findById(storeItemId).orElseThrow().getItem().getId()).orElseThrow();
        findItem.setAvailable(false);

        storeItemRepository.save(refactorStoreItem);
        itemRepository.save(findItem);

       return addNewItem(storeItemDto, managerId);

    }

    @Override
    public String setOrderStatus(String externalId, Integer managerId) {
        List<String> orderStatus = List.of(
                "Платёж успешно выполнен",
                "Ожидает доставки",
                "Доставлено"
        );

        Order order = orderRepository.findByExternalId(externalId);
        if (order == null)
            throw new RuntimeException();

        if (order.getStatus().equals("Ожидает подтверждения платежа") || order.getStatus() == null)
            return "Вы не можете изменить статус заказа!";

        String updateStatus;
        try {
            updateStatus = orderStatus.get(orderStatus.indexOf(order.getStatus()) + 1);
        } catch (Exception e) {
            return "Вы не можете изменить статус заказа!";
        }

        order.setStatus(updateStatus);
        order.setDateOfRefactoring(new Date());
        order.setManager(managerRepository.findById(managerId).orElseThrow());

        orderRepository.save(order);

        return "Статус изменён на: " + updateStatus;
    }

    @Override
    public List<ManagerOrderDto> getAllUserOrders(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
        return orderRepository.findAllByUser(user).stream()
                .map(order -> new ManagerOrderDto(order.getId(), order.getStatus(), order.getDate(), order.getDateOfRefactoring(), order.getPrice(),
                        UserDto.toUserDto(order.getUser()), AddressDto.toAddressDto(order.getAddress()), order.getExternalId(),
                        order.getStoreItem().stream()
                                .map(storeItem -> storeItem.getItem().getName()).collect(Collectors.toList()), order.getManager()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ManagerOrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new ManagerOrderDto(order.getId(), order.getStatus(), order.getDate(), order.getDateOfRefactoring(), order.getPrice(), UserDto.toUserDto(order.getUser()),
                        AddressDto.toAddressDto(order.getAddress()),
                        order.getExternalId(),
                        order.getStoreItem().stream()
                                .map(storeItem -> storeItem.getItem().getName())
                                .collect(Collectors.toList()),
                        order.getManager()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ManagerUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new ManagerUserDto(user.getId(), user.getName(), user.getSurname(), null, user.getPhoneNumber(), user.getBirthday(), user.isAvailable()))
                .collect(Collectors.toList());
    }

    @Override
    public String blockUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
        if (!user.isAvailable())
            return user.getName() + " already blocked";
        user.setAvailable(false);
        userRepository.save(user);
        return user.getName() + " blocked!";
    }

    @Override
    public String unlockUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
        if (user.isAvailable())
            return user.getName() + " not blocked";

        user.setAvailable(true);
        userRepository.save(user);
        return user.getName() + " unblocked!";
    }

    @Override
    public UserDetails loadUserByUsername(String personalNumber) throws UsernameNotFoundException {

        Manager DBManager = managerRepository.findByPersonalNumber(personalNumber);

        if (DBManager == null)
            throw new ManagerNotFound("Manager is not found");

        return org.springframework.security.core.userdetails.User.builder()
                .username(DBManager.getPersonalNumber())
                .password(DBManager.getPassword())
                .roles(Roles.MANAGER.toString())
                .build();
    }

}
