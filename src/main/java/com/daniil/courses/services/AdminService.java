package com.daniil.courses.services;

import com.daniil.courses.dto.ManagerDto;

import java.util.List;

public interface AdminService {

    /**
     * Создать нового менеджера
     */
    ManagerDto createManager(ManagerDto managerDto);

    /**
     * Получить всех менеджеров
     */
    List<ManagerDto> getAllManagers();

    /**
     * Удалить менеджера
     */
    void deleteManager(Integer managerId);

}
