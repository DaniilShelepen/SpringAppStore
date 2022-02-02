package com.daniil.courses.dal.repositories;

import com.daniil.courses.dal.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findByPersonalNumberAndDeleted(String personalNumber,boolean deleted);

    Manager findByPersonalNumber(String personalNumber);

    List<Manager> findAllByDeleted(boolean deleted);

}