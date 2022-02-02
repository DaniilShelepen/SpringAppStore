package com.daniil.courses.dal.repositories;

import com.daniil.courses.dal.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> findBasketByUserId(Integer userId);

    List<Basket> findAllByUserId(Integer userId);

    @Modifying
    void deleteByStoreItemIdAndUserId(Integer storeItemId, Integer userId);
    @Modifying
    void deleteAllByUserId(Integer userId);

    Basket findByStoreItemIdAndUserId(Integer storeItem, Integer userId);
}