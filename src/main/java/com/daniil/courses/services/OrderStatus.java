package com.daniil.courses.services;

public enum OrderStatus {
    AwaitingConfirmationOfPayment,
    PaymentRejected,
    PaymentAccepted,
    AwaitingDelivery,
    Delivered
}
