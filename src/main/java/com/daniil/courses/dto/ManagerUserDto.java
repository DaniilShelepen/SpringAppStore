package com.daniil.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerUserDto {
    protected Integer id;
    protected String name;
    protected String surname;
    protected String password;
    @NotBlank(message = "Номер телефона не может быть пустым")
    protected String phoneNumber;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate birthday;
    boolean available;


}
