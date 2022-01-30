package com.daniil.courses.services.impl;

import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.mappers.OrderConvertor;
import com.daniil.courses.mappers.StoreItemConvertor;
import com.daniil.courses.models.Order;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.repositories.OrderRepository;
import com.daniil.courses.repositories.StoreItemRepository;
import com.daniil.courses.services.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterServiceImpl implements FilterService {

    StoreItemRepository storeItemRepository;
    OrderRepository orderRepository;
    StoreItemConvertor storeItemConvertor;
    OrderConvertor orderConvertor;

    @Autowired
    public FilterServiceImpl(OrderConvertor orderConvertor,StoreItemConvertor storeItemConvertor,StoreItemRepository storeItemRepository, OrderRepository orderRepository) {
        this.storeItemRepository = storeItemRepository;
        this.orderRepository = orderRepository;
        this.storeItemConvertor = storeItemConvertor;
        this.orderConvertor = orderConvertor;
    }


    @Override
    public List<UserStoreItemDto> getAllItemsWithType(String type) {
        return storeItemRepository.findAllByAvailable(true).stream()
                .filter(storeItem -> storeItem.getItem().getType().equalsIgnoreCase(type))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getAllItemsWithDriverConfiguration(String configuration) {
        return storeItemRepository.findAllByAvailable(true).stream()
                .filter(storeItem -> storeItem.getItem().getDriverConfiguration().equalsIgnoreCase(configuration))
                .map(storeItemConvertor::convertForUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getAllItemsWithCPU(String CPU) {
        return storeItemRepository.findAllByAvailable(true).stream()
                .filter(storeItem -> storeItem.getItem().getCPU().equalsIgnoreCase(CPU))
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
    public List<UserOrderDto> filterUserOrderByStatus(Integer userId, String orderStatuses) {

        return orderRepository.findAllByUserId(userId).stream()
                .filter(order -> order.getStatus().toLowerCase().contains(orderStatuses.toLowerCase()))
                .map(orderConvertor::convertForUser)
                .collect(Collectors.toList());
    }


    @Override
    public List<UserOrderDto> filterUserOrderByDateNew(Integer userId) {

        List<UserOrderDto> finalList =
                orderRepository.findAllByUserId(userId).stream()
                        .sorted(Comparator.comparing(Order::getDate))
                        .map(orderConvertor::convertForUser)
                        .collect(Collectors.toList());
        Collections.reverse(finalList);
        return finalList;

    }

    @Override
    public List<UserOrderDto> filterUserOrderByDateOld(Integer userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .sorted(Comparator.comparing(Order::getDate))
                .map(orderConvertor::convertForUser)
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
