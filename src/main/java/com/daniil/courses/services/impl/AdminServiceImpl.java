package com.daniil.courses.services.impl;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.exceptions.ManagerIsAlreadyExists;
import com.daniil.courses.exceptions.ManagerNotFound;
import com.daniil.courses.repositories.ManagerRepository;
import com.daniil.courses.role_models.Manager;
import com.daniil.courses.security.Roles;
import com.daniil.courses.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final ManagerRepository managerRepository;

    @Override
    public ManagerDto createManager(ManagerDto managerDto) {

        Manager manager = managerRepository.findByPersonalNumber(managerDto.getPersonalNumber());
        if (manager != null)
            throw new ManagerIsAlreadyExists("Manager is already exist");

        managerRepository.save(Manager.builder()
                .userName(managerDto.getUserName())
                .password(new BCryptPasswordEncoder().encode(managerDto.getPassword()))
                .personalNumber(managerDto.getPersonalNumber())
                .build());
        return managerDto;
    }

    @Override
    public List<ManagerDto> getAllManagers() {
        return managerRepository.findAll().stream()
                .map(manager -> new ManagerDto(manager.getId(), manager.getUserName(), null, manager.getPersonalNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteManager(Integer managerId) {
        managerRepository.deleteById(managerId);
    }
}
