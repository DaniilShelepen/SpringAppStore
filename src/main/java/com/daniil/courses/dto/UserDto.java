package com.daniil.courses.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserDto {
    protected String name;
    protected String surname;
    protected String password;
    // @Pattern(value = "^1([345789])\\d{9}$")
    @NotBlank(message = "Номер телефона не может быть пустым")
    protected String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date birthday;

}
