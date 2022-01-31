package com.daniil.courses.security;


import com.daniil.courses.repositories.UserRepository;
import com.daniil.courses.role_models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        User DBUser = userRepository.findByPhoneNumber(phoneNumber);

        if (DBUser == null)
            throw new UsernameNotFoundException("User is not found");

        return org.springframework.security.core.userdetails.User.builder()
                .username(DBUser.getPhoneNumber())
                .password(DBUser.getPassword())
                .roles(Roles.USER.toString())
                .build();
    }
}
