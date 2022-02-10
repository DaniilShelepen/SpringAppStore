package com.daniil.courses.controller;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.security.AccessAdmin;
import com.daniil.courses.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @AccessAdmin
    @PostMapping("manager")
    @Operation(description = "Создание менеджера")
    public ManagerDto createManager(@RequestBody ManagerDto managerDto) {
        return adminService.createManager(managerDto);
    }

    @AccessAdmin
    @GetMapping("managers")
    @Operation(description = "Получить всех менеджеров")
    public List<ManagerDto> getAllShopManagers() {
        return adminService.getAllManagers();
    }

    @AccessAdmin
    @DeleteMapping("manager/{managerId}")
    @Operation(description = "Удалить менеджера")
    public void deleteManager(@PathVariable Integer managerId) {
        adminService.deleteManager(managerId);
    }

}
