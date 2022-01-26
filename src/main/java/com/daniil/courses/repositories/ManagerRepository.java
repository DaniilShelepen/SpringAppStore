package com.daniil.courses.repositories;

import com.daniil.courses.role_models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findByUserName(String userName);

    Manager findByPersonalNumber(String personalNumber);

    @Transactional
    void deleteById(Integer managerId);
}