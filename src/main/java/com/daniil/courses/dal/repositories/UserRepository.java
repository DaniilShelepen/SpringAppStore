package com.daniil.courses.dal.repositories;

import com.daniil.courses.dal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByPhoneNumber(String phoneNumber);

    User findByPhoneNumberAndAvailable(String phoneNumber, boolean available);

}
