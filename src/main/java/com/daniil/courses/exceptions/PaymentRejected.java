package com.daniil.courses.exceptions;

public class PaymentRejected extends RuntimeException {
    public PaymentRejected() {
        super("Ваш платёж отвергнут");
    }
}
