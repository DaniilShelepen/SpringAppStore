package com.daniil.courses.services.impl;

import com.daniil.courses.dal.entity.Order;
import com.daniil.courses.dal.entity.StoreItem;
import com.daniil.courses.dal.repositories.OrderRepository;
import com.daniil.courses.dal.repositories.StoreItemRepository;
import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.mappers.OrderConvertor;
import com.daniil.courses.mappers.StoreItemConvertor;
import com.daniil.courses.services.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {

    private final StoreItemRepository storeItemRepository;
    private final OrderRepository orderRepository;
    private final StoreItemConvertor storeItemConvertor;
    private final OrderConvertor orderConvertor;

    @Override
    public List<UserStoreItemDto> getAllItemsWithType(String type) {
        return storeItemRepository.findAllByAvailable(true).stream()
                .filter(storeItem -> storeItem.getItem().getType().toLowerCase().contains(type.toLowerCase()))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getAllItemsWithDriverConfiguration(String configuration) {
        return storeItemRepository.findAllByAvailable(true).stream()
                .filter(storeItem -> storeItem.getItem().getDriverConfiguration().toLowerCase().contains(configuration.toLowerCase()))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getAllItemsWithDescription(String description) {
        return storeItemRepository.findAllByAvailable(true).stream()
                .filter(storeItem -> storeItem.getItem().getDescription().toLowerCase().contains(description.toLowerCase()))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getAllItemsWithCPU(String CPU) {
        return storeItemRepository.findAllByAvailable(true).stream()
                .filter(storeItem -> storeItem.getItem().getCPU().toLowerCase().contains(CPU.toLowerCase()))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());
    }


    @Override
    public List<UserStoreItemDto> getAllWithReleaseDate(LocalDate date) {
        return storeItemRepository.findAllByAvailable(true).stream()
                .filter(storeItem -> storeItem.getItem().getReleaseDate().compareTo(date) > 0)
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());
    }


    @Override
    public List<ManagerOrderDto> filterUserOrderByStatus(Integer userId, String orderStatuses) {

        return orderRepository.findAllByUserId(userId).stream()
                .filter(order -> order.getStatus().toString().contains(orderStatuses.toLowerCase()))
                .map(orderConvertor::convertForManager)
                .collect(Collectors.toList());
    }


    @Override
    public List<ManagerOrderDto> filterUserOrderByDateNew(Integer userId) {

        List<ManagerOrderDto> finalList =
                orderRepository.findAllByUserId(userId).stream()
                        .sorted(Comparator.comparing(Order::getDate))
                        .map(orderConvertor::convertForManager)
                        .collect(Collectors.toList());
        Collections.reverse(finalList);
        return finalList;

    }

    @Override
    public List<ManagerOrderDto> filterUserOrderByDateOld(Integer userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .sorted(Comparator.comparing(Order::getDate))
                .map(orderConvertor::convertForManager)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getCheap() {
        return storeItemRepository.findAllByAvailable(true).stream()
                .sorted(Comparator.comparing(StoreItem::getPrice))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getExpensive() {
        List<UserStoreItemDto> list = storeItemRepository.findAllByAvailable(true).stream()
                .sorted(Comparator.comparing(StoreItem::getPrice))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());

        Collections.reverse(list);

        return list;
    }

}
