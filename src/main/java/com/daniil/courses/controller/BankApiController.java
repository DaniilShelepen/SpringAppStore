package com.daniil.courses.controller;

import com.daniil.courses.payment.WebHookAcquireRequest;
import com.daniil.courses.models.Order;
import com.daniil.courses.payment.WebHookAcquireSuccessRequest;
import com.daniil.courses.repositories.OrderRepository;
import com.daniil.courses.services.ORDER_STATUS;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

@Hidden
@RestController
@RequestMapping("api/bank/")
@RequiredArgsConstructor
@Slf4j
public class BankApiController {

    OrderRepository orderRepository;

    @Autowired
    public BankApiController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("getanswer")
    public void getAnswer(@RequestBody WebHookAcquireRequest webHookAcquireRequest) {
        Order order = orderRepository.findByExternalId(webHookAcquireRequest.getExternalId());
        if (webHookAcquireRequest instanceof WebHookAcquireSuccessRequest)
            order.setStatus(ORDER_STATUS.CONFIRMED);
        else order.setStatus(ORDER_STATUS.ERROR);
        order.setDateOfRefactoring(new Date());
        orderRepository.save(order);

    }

}
