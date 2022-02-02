package com.daniil.courses.security;

import com.daniil.courses.exceptions.ManagerNotFound;
import com.daniil.courses.dal.repositories.ManagerRepository;
import com.daniil.courses.dal.entity.Manager;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Configuration
@Data
public class ManagerDetailsServiceImpl implements UserDetailsService {
    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String personalNumber) throws UsernameNotFoundException {

        Manager DBManager = managerRepository.findByPersonalNumber(personalNumber);

        if (DBManager == null)
            throw new ManagerNotFound("Manager is not found");

        return org.springframework.security.core.userdetails.User.builder()
                .username(DBManager.getPersonalNumber())
                .password(DBManager.getPassword())
                .roles(Roles.MANAGER.toString())
                .build();
    }
}
