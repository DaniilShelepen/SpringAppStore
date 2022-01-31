package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.models.Order;

import java.util.stream.Collectors;

public interface OrderConvertor {


     ManagerOrderDto convertForManager(Order order);

     UserOrderDto convertForUser(Order order);

}
