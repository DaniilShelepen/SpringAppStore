package com.daniil.courses.bankApi;


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
//    protected String amount;
//    protected String currency;
//    protected BigDecimal value;
    protected String paymentConfirmationRedirectUrl;

}
