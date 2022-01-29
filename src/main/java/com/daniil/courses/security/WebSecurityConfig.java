package com.daniil.courses.security;

import com.daniil.courses.services.impl.ManagerServiceImpl;
import com.daniil.courses.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userService;
    private ManagerServiceImpl managerService;

    @Autowired
    public WebSecurityConfig(ManagerServiceImpl managerService,UserServiceImpl userService) {
        this.userService = userService;
        this.managerService = managerService;
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
                .antMatchers("/api/bank/getanswer").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Value(value = "${admin.login}")
    String login;
    @Value(value = "${admin.password}")
    String password;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(login)
                .password(passwordEncoder().encode(password))
                .roles(Roles.ADMIN.toString());

        auth.userDetailsService(userService);
        auth.userDetailsService(managerService);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
