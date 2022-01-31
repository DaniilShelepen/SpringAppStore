package com.daniil.courses.dto;

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
public class UserOrderDto {
    protected Integer id;
    @NotBlank
    protected ORDER_STATUS status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate date;
    protected Date dateOfRefactoring;
    protected BigDecimal price;
    protected List<String> items;
    protected AddressDto addressDto;

}
