package com.daniil.courses.dto;

import com.daniil.courses.role_models.Manager;
import com.daniil.courses.services.ORDER_STATUS;
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
    protected Integer id;
    @NotBlank
    protected ORDER_STATUS status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate date;
    protected Date dateOfRefactoring;
    protected BigDecimal price;
    protected ManagerUserDto user;
    protected AddressDto address;
    protected String externalId;
    protected List<String> items;
    protected Manager manager;


}
