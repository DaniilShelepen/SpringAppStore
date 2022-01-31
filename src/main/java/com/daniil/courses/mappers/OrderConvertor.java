package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.models.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderConvertor {

    private final AddressConvertor addressConvertor;
    private final UserConvertor userConvertor;

    @Autowired
    public OrderConvertor(AddressConvertor addressConvertor, UserConvertor userConvertor) {
        this.addressConvertor = addressConvertor;
        this.userConvertor = userConvertor;
    }

    public ManagerOrderDto convertForManager(Order order) {
        order.getManager().setPassword(null);
        return new ManagerOrderDto(order.getId(), order.getStatus(), order.getDate(), order.getDateOfRefactoring(), order.getPrice(),
                userConvertor.convertForManager(order.getUser()), addressConvertor.convert(order.getAddress()), order.getExternalId(),
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
