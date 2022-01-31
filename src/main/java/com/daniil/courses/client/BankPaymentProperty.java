package com.daniil.courses.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bank")
@Data
public class BankPaymentProperty {
    private String account;
    private String secret;
    private String url;
}
