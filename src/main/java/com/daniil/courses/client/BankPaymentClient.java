package com.daniil.courses.client;

import com.daniil.courses.client.model.PaymentRequest;
import com.daniil.courses.client.model.PaymentResponce;

public interface BankPaymentClient {
    PaymentResponce payment(PaymentRequest paymentRequest);
}
