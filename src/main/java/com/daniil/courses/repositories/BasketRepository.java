package com.daniil.courses.repositories;

import com.daniil.courses.models.Basket;
import com.daniil.courses.models.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    List<Basket> findBasketByUserId(Integer user_id);
    Basket findByUserId(Integer user_id);

    @Modifying
    void deleteByStoreItemIdAndUserId(Integer storeItemId, Integer userId);
    @Modifying
    void deleteAllByUserId(Integer userId);

    Basket findByStoreItemIdAndUserId(Integer storeItem, Integer userId);
}