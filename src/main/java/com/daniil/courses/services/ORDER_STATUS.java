package com.daniil.courses.services;

public enum ORDER_STATUS {
    AWAITING_OF_CONFIRM("Ожидает подтверждения платежа"),
    CONFIRMED("Платёж успешно выполнен"),
    AWAITING_OF_DELIVERY("Ожидает доставки"),
    DELIVERED("Доставлено");

    private final String description;

    ORDER_STATUS(String description) {
        this.description = description;
    }

    public String getDescription() {return description;}
}
