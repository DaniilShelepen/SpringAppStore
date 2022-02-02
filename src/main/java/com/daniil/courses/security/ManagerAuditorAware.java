package com.daniil.courses.security;


import com.daniil.courses.dal.entity.Manager;
import com.daniil.courses.dal.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ManagerAuditorAware implements AuditorAware<Manager> {

    private final ManagerRepository managerRepository;

    @Override
    public Optional<Manager> getCurrentAuditor() {
        UserDetails userDetails;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Manager manager = managerRepository.findByPersonalNumber(userDetails.getUsername());
            return Optional.ofNullable(manager);
        } catch (Exception e) {
            return Optional.empty();
        }

    }
}