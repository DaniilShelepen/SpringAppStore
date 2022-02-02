package com.daniil.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.daniil.courses.dal.entity")
public class FinalApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}



