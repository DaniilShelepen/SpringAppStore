package com.daniil.courses.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    protected String id;
    protected String externalId;
    protected Amount amount;
    protected String paymentConfirmationRedirectUrl;

}
@Data
@AllArgsConstructor
@NoArgsConstructor
class Amount{
    protected String currency;
    protected BigDecimal value;
}
