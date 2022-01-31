package com.daniil.courses.client;

import com.daniil.courses.client.model.PaymentRequest;
import com.daniil.courses.client.model.PaymentResponse;
import com.daniil.courses.exceptions.PaymentRejected;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Data
@Slf4j
public class BankPaymentClientImpl implements BankPaymentClient {
    private final RestTemplate restTemplate;
    private final BankPaymentProperty bankPaymentProperty;
    @Override
    public PaymentResponse payment(PaymentRequest paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("api-secret", bankPaymentProperty.getSecret());
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);
        try {
            return restTemplate.postForObject(bankPaymentProperty.getUrl(), entity, PaymentResponse.class);
        }catch (Exception e){
            throw new PaymentRejected();
        }
    }
}
