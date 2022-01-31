package com.daniil.courses.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BankPaymentProperty.class)
public class BankPaymentConfig {
}
