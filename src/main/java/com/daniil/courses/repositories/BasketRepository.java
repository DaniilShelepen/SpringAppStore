package com.daniil.courses.repositories;

import com.daniil.courses.models.Basket;
import com.daniil.courses.models.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {


    List<Basket> findBasketByUserId(Integer user_id);

    @Transactional
    void deleteByStoreItemIdAndUserId(Integer storeItem_id, Integer user_id);

    @Transactional
    void deleteAllByUserId(Integer userId);

    List<Basket> findAllByUserId(Integer user_id);

    Basket findByUserId(Integer user_id);

    List<Basket> findAllByStoreItemAndUserId(StoreItem storeItem, Integer user_id);
}