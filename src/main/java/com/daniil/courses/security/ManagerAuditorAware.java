package com.daniil.courses.security;

import com.daniil.courses.role_models.Manager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
//
//@Configuration
//public class ManagerAuditorAware implements AuditorAware<Manager> {
//
//    @Override
//    public Optional<Manager> getCurrentAuditor() {
//
//        return Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(Manager.class::cast);
//    }
//
//}