package com.daniil.courses.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateOrderResponse {
    private String paymentConfirmationUrl;
    private BigDecimal price;

}
