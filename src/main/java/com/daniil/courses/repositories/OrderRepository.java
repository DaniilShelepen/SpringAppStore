package com.daniil.courses.repositories;

import com.daniil.courses.models.Order;
import com.daniil.courses.role_models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(Integer userId);

    List<Order> findAllByUser(User user);

    Order findByExternalId(String externalId);

}