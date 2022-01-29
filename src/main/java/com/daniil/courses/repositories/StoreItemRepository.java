package com.daniil.courses.repositories;

import com.daniil.courses.models.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface StoreItemRepository extends JpaRepository<StoreItem, Integer> {
    StoreItem findByIdAndAvailable(Integer id, boolean available);
}