package com.daniil.courses.dto;

import com.daniil.courses.role_models.Manager;
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

    public static ManagerDto toShortManagerDto(Manager manager) {
        return new ManagerDto(manager.getId(), manager.getUserName(), null, manager.getPersonalNumber());
    }

}
