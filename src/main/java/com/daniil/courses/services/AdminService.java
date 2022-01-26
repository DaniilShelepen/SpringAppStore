package com.daniil.courses.services;

import com.daniil.courses.dto.ManagerDto;

import java.util.List;

public interface AdminService {
    ManagerDto createManager(ManagerDto managerDto);

    List<ManagerDto> getAllManagers();

    void deleteManager(Integer managerId);

}
