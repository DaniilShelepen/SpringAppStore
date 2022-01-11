package com.daniil.courses.repositories;

import com.daniil.courses.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(Integer userId);

}