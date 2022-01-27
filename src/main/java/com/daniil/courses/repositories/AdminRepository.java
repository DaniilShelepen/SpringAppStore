package com.daniil.courses.repositories;

import com.daniil.courses.role_models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    List<Admin> findByName(String name);
}
