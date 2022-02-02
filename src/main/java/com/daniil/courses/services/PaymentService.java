package com.daniil.courses.services;

import com.daniil.courses.dto.CreateOrderResponse;
import com.daniil.courses.dal.entity.Order;


public interface PaymentService {
    /**
     * Процесс оплаты в банке
     */
    CreateOrderResponse payToBank(Order order,String bankCard);
}
