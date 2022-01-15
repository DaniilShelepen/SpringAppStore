package com.daniil.courses.controller;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("createManager")
    public ManagerDto createManager(@RequestBody ManagerDto managerDto) {
        return adminService.createManager(managerDto);
    }

}
