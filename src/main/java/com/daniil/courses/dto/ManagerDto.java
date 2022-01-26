package com.daniil.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class ManagerDto {
    protected Integer id;
    protected String userName;
    protected String password;
    protected String personalNumber;

}
