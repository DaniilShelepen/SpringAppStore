package com.daniil.courses.mappers.impl;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.mappers.AddressConvertor;
import com.daniil.courses.mappers.OrderConvertor;
import com.daniil.courses.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConvertorImpl implements OrderConvertor {

    private final AddressConvertor addressConvertor;
    private final UserConvertorImpl userConvertorImpl;


    public ManagerOrderDto convertForManager(Order order) {
        order.getManager().setPassword(null);
        return new ManagerOrderDto(order.getId(), order.getStatus(), order.getDate(), order.getDateOfRefactoring(), order.getPrice(),
                userConvertorImpl.convertForManager(order.getUser()), addressConvertor.convert(order.getAddress()), order.getExternalId(),
                order.getStoreItem().stream()
                        .map(storeItem -> storeItem.getItem().getName()).collect(Collectors.toList()), order.getManager());
    }

    public UserOrderDto convertForUser(Order order) {
        return new UserOrderDto(order.getId(), order.getStatus(), order.getDate(), order.getDateOfRefactoring(), order.getPrice(),
                order.getStoreItem().stream()
                        .map(storeItem -> storeItem.getItem().getName()).collect(Collectors.toList()),
                addressConvertor.convert(order.getAddress()));
    }
}
