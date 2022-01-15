package com.daniil.courses.services.impl;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.repositories.ManagerRepository;
import com.daniil.courses.role_models.Manager;
import com.daniil.courses.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final ManagerRepository managerRepository;

    @Override
    public ManagerDto createManager(ManagerDto managerDto) {
        managerRepository.save(Manager.builder()
                .userName(managerDto.getUserName())
                .password(managerDto.getPassword())
                .build());
        return managerDto;
    }
}
