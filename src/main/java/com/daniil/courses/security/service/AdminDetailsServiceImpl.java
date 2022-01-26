package com.daniil.courses.security.service;

import com.daniil.courses.role_models.Admin;
import com.daniil.courses.security.Roles;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AdminDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = Admin.builder().build();
        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getName())
                .password(new BCryptPasswordEncoder().encode(admin.getPassword()))
                .roles(Roles.ADMIN.toString())
                .build();
    }
}
