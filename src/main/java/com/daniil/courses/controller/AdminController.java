package com.daniil.courses.controller;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/")
@RequiredArgsConstructor
public class AdminController {


    private final AdminService adminService;

    @PostMapping("createManager")
    public ManagerDto createManager(@RequestBody ManagerDto managerDto) {
        return adminService.createManager(managerDto);
    }

    @GetMapping("getManagers")
    public List<ManagerDto> getAllShopManagers() {
        return adminService.getAllManagers();
    }

    @DeleteMapping("deleteManager/{managerId}")
    public void deleteManager(@PathVariable Integer managerId) {
        adminService.deleteManager(managerId);
    }

}
