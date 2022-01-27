package com.daniil.courses.security.service;

import com.daniil.courses.repositories.AdminRepository;
import com.daniil.courses.repositories.ManagerRepository;
import com.daniil.courses.role_models.Admin;
import com.daniil.courses.security.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class AdminDetailsServiceImpl implements UserDetailsService {

    AdminRepository adminRepository;

    @Autowired
    public AdminDetailsServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<Admin> admins  = adminRepository.findByName(username);


        return org.springframework.security.core.userdetails.User.builder()
                .username(admins.get(0).getName())
                .password(new BCryptPasswordEncoder().encode(admins.get(0).getPassword()))
                .roles(Roles.ADMIN.toString())
                .build();
    }
}
