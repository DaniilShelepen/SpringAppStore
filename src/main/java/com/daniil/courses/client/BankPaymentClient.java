package com.daniil.courses.client;

import com.daniil.courses.client.model.PaymentRequest;
import com.daniil.courses.client.model.PaymentResponse;

public interface BankPaymentClient {
    PaymentResponse payment(PaymentRequest paymentRequest);
}
