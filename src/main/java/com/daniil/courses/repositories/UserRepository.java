package com.daniil.courses.repositories;

import com.daniil.courses.role_models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {



}
