package com.daniil.courses.controller;

import com.daniil.courses.bankApi.WebHookAcquireRequest;
import com.daniil.courses.models.Order;
import com.daniil.courses.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
        log.warn("SUYYYYYYYYYYYYYYKAAAAAAA");
        Order order = orderRepository.findById(1).orElseThrow();
        order.setStatus(webHookAcquireRequest.getDescription());
        orderRepository.save(order);

    }

}
