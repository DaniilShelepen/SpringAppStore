package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.models.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderConvertor {


    private final ModelMapper mapper;

    public OrderConvertor() {
        this.mapper = new ModelMapper();
    }

    public ManagerOrderDto convertForManager(Order order) {
        return mapper.map(order, ManagerOrderDto.class);
    }

    public UserOrderDto convertForUser(Order order) {
        return mapper.map(order, UserOrderDto.class);
    }
}
