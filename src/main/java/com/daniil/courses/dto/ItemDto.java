package com.daniil.courses.dto;

import com.daniil.courses.models.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

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
