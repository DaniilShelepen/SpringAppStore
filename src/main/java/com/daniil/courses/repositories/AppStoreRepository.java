package com.daniil.courses.repositories;

import com.daniil.courses.models.AppStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppStoreRepository extends JpaRepository<AppStore, Integer> {
}