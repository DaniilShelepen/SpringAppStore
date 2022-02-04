package com.daniil.courses.dal.repositories;

import com.daniil.courses.dal.entity.Order;
import com.daniil.courses.dal.entity.User;
import com.daniil.courses.dto.ORDER_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserId(Integer userId);

    List<Order> findAllByUser(User user);

    Order findByExternalId(String externalId);

    @Transactional
    @Modifying
    @Query("update  order_entity o set o.status = ?2 where o.id=?1")
    void setStatus(Integer id, ORDER_STATUS status);


}