package com.daniil.courses.repositories;

import com.daniil.courses.role_models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;

public interface UserRepository extends JpaRepository<User, Integer> {

boolean findByPhoneNumber(@NotBlank(message = "Номер телефона не может быть пустым") String phoneNumber);

}
