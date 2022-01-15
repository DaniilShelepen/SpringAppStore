package com.daniil.courses.services.impl;

import com.daniil.courses.models.Order;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.models.UserOrder;
import com.daniil.courses.repositories.OrderRepository;
import com.daniil.courses.repositories.StoreItemRepository;
import com.daniil.courses.role_models.User;
import com.daniil.courses.services.FilterService;
import com.daniil.courses.services.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<StoreItem> getAllItemsWithType(String type) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreItem> getAllItemsWithDriverConfiguration(String configuration) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getDriverConfiguration().equals(configuration))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreItem> getAllItemsWithCPU(String CPU) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getCPU().equals(CPU))
                .collect(Collectors.toList());
    }


    @Override
    public List<StoreItem> getAllWithReleaseDate(Date date) {
        return storeItemRepository.findAll().stream()
                .filter(storeItem -> storeItem.getItem().getReleaseDate().compareTo(date) > 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOrder> filterUserOrderByStatus(User user, OrderStatus... orderStatuses) {


        boolean a = orderRepository.findAllByUserId(user.getId()).stream().anyMatch(
                order -> order.getStatus().contains(Arrays.toString(orderStatuses)));

        if(a) log.info("aaaaaaaaaaaaaaaaaaaaaa");//TODO говно доделать

        return orderRepository.findAllByUserId(user.getId()).stream()
                .filter(order -> order.getStatus().contains(Arrays.toString(orderStatuses)))
                .map(order -> new UserOrder(order.getStatus(), order.getDate(), order.getDateOfRefactoring()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserOrder> filterUserOrderByDateNew(User user) {

        List<UserOrder> finalList =
                orderRepository.findAllByUserId(user.getId()).stream()
                        .sorted(Comparator.comparing(Order::getDate))
                        .map(order -> new UserOrder(order.getStatus(), order.getDate(), order.getDateOfRefactoring()))
                        .collect(Collectors.toList());
        Collections.reverse(finalList);
        return finalList;

    }

    @Override
    public List<UserOrder> filterUserOrderByDateOld(User user) {
        return orderRepository.findAllByUserId(user.getId()).stream()
                .sorted(Comparator.comparing(Order::getDate))
                .map(order -> new UserOrder(order.getStatus(), order.getDate(), order.getDateOfRefactoring()))
                .collect(Collectors.toList());
    }
}
