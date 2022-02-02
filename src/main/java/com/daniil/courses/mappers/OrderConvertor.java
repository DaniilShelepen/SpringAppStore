package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.dal.entity.Order;

public interface OrderConvertor {


     ManagerOrderDto convertForManager(Order order);

     UserOrderDto convertForUser(Order order);

}
