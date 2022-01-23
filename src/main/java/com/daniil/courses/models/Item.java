package com.daniil.courses.models;


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
    protected String name;
    protected String description;
    protected String type;
    protected String driverConfiguration;
    protected String CPU;


    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate releaseDate;


}
