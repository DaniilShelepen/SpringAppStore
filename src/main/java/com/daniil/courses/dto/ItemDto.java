package com.daniil.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    protected String name;
    protected String description;
    protected String type;
    protected String driverConfiguration;
    protected String CPU;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate releaseDate;

}
