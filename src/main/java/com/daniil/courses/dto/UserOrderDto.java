package com.daniil.courses.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDto {
    protected Integer id;
    @NotBlank
    protected String status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate date;
    @DateTimeFormat(pattern = "dd.MM.yyy")
    protected LocalDate dateOfRefactoring;
    protected BigDecimal price;
    protected List<String> items;

}
