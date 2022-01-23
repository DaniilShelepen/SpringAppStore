package com.daniil.courses.services.impl;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.models.Order;
import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.repositories.OrderRepository;
import com.daniil.courses.repositories.StoreItemRepository;
import com.daniil.courses.role_models.User;
import com.daniil.courses.services.FilterService;
import com.daniil.courses.services.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j//TODO убери потом
public class FilterServiceImpl implements FilterService {

    StoreItemRepository storeItemRepository;
    OrderRepository orderRepository;

    @Autowired
    public FilterServiceImpl(StoreItemRepository storeItemRepository, OrderRepository orderRepository) {
        this.storeItemRepository = storeItemRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public List<UserStoreItemDto> getAllItemsWithType(String type) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getType().equals(type))
                .map(storeItem -> new UserStoreItemDto(ItemDto.toItemDto(storeItem.getItem()),storeItem.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getAllItemsWithDriverConfiguration(String configuration) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getDriverConfiguration().equals(configuration))
                .map(storeItem -> new UserStoreItemDto(ItemDto.toItemDto(storeItem.getItem()),storeItem.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserStoreItemDto> getAllItemsWithCPU(String CPU) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getCPU().equals(CPU))
                .map(storeItem -> new UserStoreItemDto(ItemDto.toItemDto(storeItem.getItem()),storeItem.getPrice()))
                .collect(Collectors.toList());
    }


    @Override
    public List<UserStoreItemDto> getAllWithReleaseDate(LocalDate date) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getReleaseDate().compareTo(date) > 0)
                .map(storeItem -> new UserStoreItemDto(ItemDto.toItemDto(storeItem.getItem()),storeItem.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOrderDto> filterUserOrderByStatus(User user, OrderStatus... orderStatuses) {


        boolean a = orderRepository.findAllByUserId(user.getId()).stream().anyMatch(
                order -> order.getStatus().contains(Arrays.toString(orderStatuses)));

        if(a) log.info("aaaaaaaaaaaaaaaaaaaaaa");//TODO говно доделать

        return orderRepository.findAllByUserId(user.getId()).stream()
                .filter(order -> order.getStatus().contains(Arrays.toString(orderStatuses)))
                .map(order -> new UserOrderDto(order.getStatus(), order.getDate(), order.getDateOfRefactoring(),order.getPrice(),
                        order.getStoreItem().stream()
                                .map(storeItem -> storeItem.getItem().getName())
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOrderDto> filterUserOrderByDateNew(User user) {

        List<UserOrderDto> finalList =
                orderRepository.findAllByUserId(user.getId()).stream()
                        .sorted(Comparator.comparing(Order::getDate))
                        .map(order -> new UserOrderDto(order.getStatus(), order.getDate(), order.getDateOfRefactoring(),order.getPrice(),
                                order.getStoreItem().stream()
                                        .map(storeItem -> storeItem.getItem().getName())
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList());
        Collections.reverse(finalList);
        return finalList;

    }

    @Override
    public List<UserOrderDto> filterUserOrderByDateOld(User user) {
        return orderRepository.findAllByUserId(user.getId()).stream()
                .sorted(Comparator.comparing(Order::getDate))
                .map(order -> new UserOrderDto(order.getStatus(), order.getDate(), order.getDateOfRefactoring(),order.getPrice(),
                        order.getStoreItem().stream()
                                .map(storeItem -> storeItem.getItem().getName())
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
