package com.daniil.courses;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing
@EntityScan({"com.daniil.courses.models","com.daniil.courses.role_models"})
public class FinalApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalApplication.class, args);
    }

}
