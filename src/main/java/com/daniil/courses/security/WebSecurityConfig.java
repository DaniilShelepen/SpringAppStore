package com.daniil.courses.security;

import com.daniil.courses.repositories.AdminRepository;
import com.daniil.courses.role_models.Admin;
import com.daniil.courses.security.service.AdminDetailsServiceImpl;
import com.daniil.courses.security.service.ManagerDetailsServiceImpl;
import com.daniil.courses.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private ManagerDetailsServiceImpl managerDetailsService;
    private AdminDetailsServiceImpl adminDetailsService;
    private AdminRepository adminRepository;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, ManagerDetailsServiceImpl managerDetailsService, AdminDetailsServiceImpl adminDetailsService, AdminRepository adminRepository) {
        this.userDetailsService = userDetailsService;
        this.managerDetailsService = managerDetailsService;
        this.adminDetailsService = adminDetailsService;
        this.adminRepository = adminRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/api/users/createUser").permitAll()
                .antMatchers("/api/users/storeItems").permitAll()
                .antMatchers("/api/filter/notAuthorized/**").permitAll()
                .antMatchers("/api/admin/createManager").permitAll()
                .antMatchers("api/bank/getAnswer").hasRole("NAN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        adminRepository.save(Admin.builder().build());
        auth.userDetailsService(userDetailsService);
        auth.userDetailsService(managerDetailsService);
        auth.userDetailsService(adminDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
