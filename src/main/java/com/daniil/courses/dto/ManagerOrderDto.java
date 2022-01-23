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
public class ManagerOrderDto {
    @NotBlank
    protected String status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate date;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate dateOfRefactoring;
    protected BigDecimal price;
    protected UserDto user;
    protected AddressDto address;
    protected String externalId;
    protected List<String> items;


}
