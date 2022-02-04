package com.daniil.courses.services.impl;

import com.daniil.courses.client.BankPaymentClient;
import com.daniil.courses.client.BankPaymentProperty;
import com.daniil.courses.client.model.Amount;
import com.daniil.courses.client.model.PaymentRequest;
import com.daniil.courses.client.model.PaymentResponse;
import com.daniil.courses.dal.entity.Order;
import com.daniil.courses.dal.repositories.OrderRepository;
import com.daniil.courses.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final BankPaymentClient bankPaymentClient;
    private final BankPaymentProperty bankPaymentProperty;
    private final OrderRepository orderRepository;


    @Override
    public PaymentResponse payToBank(Integer orderId, String bankCard) {

        Order order = orderRepository.getById(orderId);

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .accountId(bankCard)
                .amount(Amount.builder()
                        .currency("USD")
                        .value(order.getPrice())
                        .build())
                .externalId(order.getExternalId())
                .purpose("Purchase in AppStore")
                .acquireWebHook(bankPaymentProperty.getControllerURL())
                .build();

        return bankPaymentClient.payment(paymentRequest);
    }
}
