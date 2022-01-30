package com.daniil.courses.dto;

import com.daniil.courses.role_models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    protected Integer id;
    protected String name;
    protected String surname;
    protected String password;
    // @Pattern(value = "^1([345789])\\d{9}$")
    @NotBlank(message = "Номер телефона не может быть пустым")
    protected String phoneNumber;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate birthday;


}
