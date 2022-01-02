package com.daniil.courses.repositories;

import com.daniil.courses.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {


    List<Basket> findBasketByUserId(Integer user_id);

    @Transactional
    void deleteByStoreItemIdAndUserId(Integer storeItem_id, Integer user_id);

    @Transactional
    void deleteAllByUserId(Integer user_id);
}