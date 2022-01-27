package com.daniil.courses.controller;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.repositories.AdminRepository;
import com.daniil.courses.security.AccessAdmin;
import com.daniil.courses.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/admin/")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final AdminService adminService;
    private final AdminRepository adminRepository;

    @AccessAdmin
    @PostMapping("createManager")
    public ManagerDto createManager(@RequestBody ManagerDto managerDto) {
        return adminService.createManager(managerDto);
    }

   // @AccessAdmin
    @GetMapping("getManagers")
    public List<ManagerDto> getAllShopManagers(Principal principal) {
        log.warn(principal.getName());

        log.warn(adminRepository.findByName(principal.getName()).toString());
        return adminService.getAllManagers();
    }

    @AccessAdmin
    @DeleteMapping("deleteManager/{managerId}")
    public void deleteManager(@PathVariable Integer managerId) {
        adminService.deleteManager(managerId);
    }

}
