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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private ManagerDetailsServiceImpl managerDetailsService;
    private AdminDetailsServiceImpl adminDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, ManagerDetailsServiceImpl managerDetailsService, AdminDetailsServiceImpl adminDetailsService) {
        this.userDetailsService = userDetailsService;
        this.managerDetailsService = managerDetailsService;
        this.adminDetailsService = adminDetailsService;
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
                .antMatchers("/api/filter/notAuthorized/**").hasRole("MANAGER")
                .antMatchers("/api/admin/createManager").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {



        auth.userDetailsService(userDetailsService);
        auth.userDetailsService(managerDetailsService);
        //auth.userDetailsService(adminDetailsService);
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
