package com.daniil.courses.services;

import com.daniil.courses.dto.CreateOrderResponse;
import com.daniil.courses.models.Order;

public interface PaymentService {
    CreateOrderResponse payToBank(Order order,String bankCard);
}
