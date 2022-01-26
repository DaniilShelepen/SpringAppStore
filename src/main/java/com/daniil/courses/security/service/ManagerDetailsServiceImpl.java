package com.daniil.courses.security.service;

import com.daniil.courses.exceptions.ManagerNotFound;
import com.daniil.courses.repositories.ManagerRepository;
import com.daniil.courses.role_models.Manager;
import com.daniil.courses.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class ManagerDetailsServiceImpl implements UserDetailsService {
    ManagerRepository managerRepository;

    @Autowired
    public ManagerDetailsServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Manager DBManager = managerRepository.findByUserName(username);

        if (DBManager == null)
            throw new ManagerNotFound("Manager is not found");

        return org.springframework.security.core.userdetails.User.builder()
                .username(DBManager.getUserName())
                .password(new BCryptPasswordEncoder().encode(DBManager.getPassword()))
                .roles(Roles.MANAGER.toString())
                .build();
    }


}

