package com.daniil.courses.dal.repositories;

import com.daniil.courses.dal.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreItemRepository extends JpaRepository<StoreItem, Integer> {
    StoreItem findByIdAndAvailable(Integer id, boolean available);

    List<StoreItem> findAllByAvailable(boolean available);
}