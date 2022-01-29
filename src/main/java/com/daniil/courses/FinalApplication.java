package com.daniil.courses;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.repositories.ManagerRepository;
import com.daniil.courses.role_models.Manager;
import com.daniil.courses.services.ManagerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan({"com.daniil.courses.models", "com.daniil.courses.role_models"})
public class FinalApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(ManagerService managerService, ManagerRepository managerRepository) {
        return args -> {
            Manager manager = Manager.builder()
                    .userName("Oleg")
                    .password(new BCryptPasswordEncoder().encode("olega1325"))
                    .personalNumber("MainManager")
                    .build();
            managerRepository.save(manager);

            managerService.addNewItem(new ManagerStoreItemDto(null,
                    new ItemDto("Ноутбук Apple Macbook Air 13\" M1 2020 MGN63", "13.3\" 2560 x 1600 IPS, 60 Гц, несенсорный, Apple M1 3200 МГц, 8 ГБ, SSD 256 ГБ, видеокарта встроенная, Mac OS, цвет крышки серый",
                            "laptop", "SSD 256", "Apple M1", LocalDate.of(2020,3,15)),
                    BigDecimal.valueOf(2000),
                    true,
                    ManagerDto.toShortManagerDto(manager)),
                    manager.getId());

        };
    }


}
