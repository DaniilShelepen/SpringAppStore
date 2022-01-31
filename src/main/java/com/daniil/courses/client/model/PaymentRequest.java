package com.daniil.courses.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PaymentRequest {

    protected String accountId;
    protected String externalId;
    protected Amount amount;
    protected String purpose;
    protected String acquireWebHook;
    protected String paymentConfirmationRedirectUrl;

}
