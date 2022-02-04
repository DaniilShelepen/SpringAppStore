package com.daniil.courses.services;

import com.daniil.courses.client.model.PaymentResponse;


public interface PaymentService {
    /**
     * Процесс оплаты в банке
     * @return
     */
    PaymentResponse payToBank(Integer orderId, String bankCard);
}
