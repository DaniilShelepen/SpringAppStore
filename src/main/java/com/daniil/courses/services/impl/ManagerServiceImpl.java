package com.daniil.courses.services.impl;

import com.daniil.courses.dal.entity.Item;
import com.daniil.courses.dal.entity.Order;
import com.daniil.courses.dal.entity.StoreItem;
import com.daniil.courses.dal.entity.User;
import com.daniil.courses.dal.repositories.*;
import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.ManagerUserDto;
import com.daniil.courses.dto.ORDER_STATUS;
import com.daniil.courses.exceptions.StoreItemIsNotFound;
import com.daniil.courses.exceptions.UserNotFound;
import com.daniil.courses.mappers.OrderConvertor;
import com.daniil.courses.mappers.StoreItemConvertor;
import com.daniil.courses.mappers.UserConvertor;
import com.daniil.courses.services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final ItemRepository itemRepository;
    private final ManagerRepository managerRepository;
    private final StoreItemRepository storeItemRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderConvertor orderConvertor;
    private final StoreItemConvertor storeItemConvertor;
    private final UserConvertor userConvertor;


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
                .build();

        itemRepository.save(item);
        storeItemRepository.save(storeItem);

        return storeItemRepository.findById(storeItem.getId()).stream()
                .map(storeItemConvertor::convertForManager).findFirst().orElseThrow();

    }

    @Override
    public void setAvailable(Integer storeItemId, boolean available, Integer managerId) {
        StoreItem storeItem1 = storeItemRepository.findById(storeItemId).orElseThrow(() -> new StoreItemIsNotFound("Store item is not found"));
        storeItem1.setAvailable(available);
        storeItemRepository.save(storeItem1);
    }

    @Override
    public List<ManagerStoreItemDto> viewAllStoreItems() {
        return storeItemRepository.findAll().stream()
                .map(storeItemConvertor::convertForManager)
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

        Order order = orderRepository.findByExternalId(externalId);
        if (order == null)
            throw new NotFoundException("Not found");

        List<ORDER_STATUS> orderStatus = List.of(
                ORDER_STATUS.CONFIRMED,
                ORDER_STATUS.AWAITING_OF_DELIVERY,
                ORDER_STATUS.DELIVERED
        );

        if (order.getStatus().equals(ORDER_STATUS.AWAITING_OF_CONFIRM) || order.getStatus().equals(ORDER_STATUS.ERROR))
            return "Вы не можете изменить статус заказа!";

        ORDER_STATUS updateStatus;
        try {
            updateStatus = orderStatus.get(orderStatus.indexOf(order.getStatus()) + 1);
        } catch (Exception e) {
            return "Вы не можете изменить статус заказа!";
        }

        order.setStatus(updateStatus);
        orderRepository.save(order);//todo тут херня

        return "Статус изменён на: " + updateStatus;
    }

    @Override
    public List<ManagerOrderDto> getAllUserOrders(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("User is not found"));
        return orderRepository.findAllByUser(user).stream()
                .map(orderConvertor::convertForManager)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManagerOrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderConvertor::convertForManager)
                .collect(Collectors.toList());
    }

    @Override
    public List<ManagerUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userConvertor::convertForManager)
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
}
