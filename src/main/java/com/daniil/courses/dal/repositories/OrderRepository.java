package com.daniil.courses.dal.repositories;

import com.daniil.courses.dal.entity.Order;
import com.daniil.courses.dal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(Integer userId);

    List<Order> findAllByUser(User user);

    Order findByExternalId(String externalId);

}