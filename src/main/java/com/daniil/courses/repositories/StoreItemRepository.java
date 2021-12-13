package com.daniil.courses.repositories;

import com.daniil.courses.models.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreItemRepository extends JpaRepository<StoreItem, Integer> {
}