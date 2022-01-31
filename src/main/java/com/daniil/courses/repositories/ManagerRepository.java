package com.daniil.courses.repositories;

import com.daniil.courses.role_models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findByPersonalNumberAndDeleted(String personalNumber,boolean deleted);

    Manager findByPersonalNumber(String personalNumber);

    List<Manager> findAllByDeleted(boolean deleted);

}