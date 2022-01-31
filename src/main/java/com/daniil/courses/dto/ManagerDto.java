package com.daniil.courses.dto;

import com.daniil.courses.role_models.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {
    protected Integer id;
    protected String userName;
    protected String password;
    protected String personalNumber;

}
