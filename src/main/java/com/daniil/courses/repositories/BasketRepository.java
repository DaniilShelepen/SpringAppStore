package com.daniil.courses.repositories;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.Basket;
import com.daniil.courses.models.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Integer> {

    @Query(value = "SELECT * from Basket where user_id = ?1",nativeQuery = true)
    List<Basket> getBasketByUserId(Integer userId);

    @Query(value = "SELECT * from Basket where storeItem = ?1",nativeQuery = true)
    Basket findBasketItemByStoreItem(StoreItem storeItem);

//    @Query(value = "delete * from Basket where storeItem = ?1 and user_id",nativeQuery = true)
//    void deleteAllByUserId(StoreItem storeItem);
}