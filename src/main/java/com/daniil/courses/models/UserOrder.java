package com.daniil.courses.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserOrder {
    protected String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date dateOfRefactoring;
}
