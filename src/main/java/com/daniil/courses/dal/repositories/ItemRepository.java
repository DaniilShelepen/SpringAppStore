package com.daniil.courses.dal.repositories;

import com.daniil.courses.dal.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}