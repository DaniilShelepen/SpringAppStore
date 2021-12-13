package com.daniil.courses.repositories;

import com.daniil.courses.models.Item;
import com.daniil.courses.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}