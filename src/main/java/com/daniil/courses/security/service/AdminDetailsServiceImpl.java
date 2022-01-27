package com.daniil.courses.security.service;

import com.daniil.courses.security.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminDetailsServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return org.springframework.security.core.userdetails.User.builder()
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .roles(Roles.ADMIN.toString())
                .build();
    }
}
