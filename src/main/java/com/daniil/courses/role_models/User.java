package com.daniil.courses.role_models;

import lombok.Getter;
import lombok.Setter;
import org.intellij.lang.annotations.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class User {
    protected String userName;
    protected String password;
    @Pattern(value = "^1([345789])\\d{9}$")
    @NotBlank(message = "Номер телефона не может быть пустым")
    protected String phoneNumber;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
