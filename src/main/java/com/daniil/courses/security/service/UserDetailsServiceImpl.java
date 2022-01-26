package com.daniil.courses.security.service;

import com.daniil.courses.repositories.UserRepository;
import com.daniil.courses.role_models.User;
import com.daniil.courses.security.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        User DBUser = userRepository.findByPhoneNumber(phoneNumber);

        if (DBUser == null)
            throw new UsernameNotFoundException("User is not found");

        return org.springframework.security.core.userdetails.User.builder()
                .username(DBUser.getPhoneNumber())
                .password(new BCryptPasswordEncoder().encode(DBUser.getPassword()))
                .roles(Roles.USER.toString())
                .build();
    }


}
