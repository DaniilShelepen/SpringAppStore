package com.daniil.courses.dal.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String type;
    private String driverConfiguration;
    private String CPU;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;
    private boolean available;


}
