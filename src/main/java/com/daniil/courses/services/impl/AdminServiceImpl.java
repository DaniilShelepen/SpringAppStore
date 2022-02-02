package com.daniil.courses.services.impl;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.exceptions.ManagerIsAlreadyExists;
import com.daniil.courses.exceptions.ManagerNotFound;
import com.daniil.courses.mappers.ManagerConvertor;
import com.daniil.courses.dal.repositories.ManagerRepository;
import com.daniil.courses.dal.entity.Manager;
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
    private final ManagerConvertor managerConvertor;

    @Override
    public ManagerDto createManager(ManagerDto managerDto) {

        Manager manager = managerRepository.findByPersonalNumber(managerDto.getPersonalNumber());

        if (manager != null)
            throw new ManagerIsAlreadyExists("Manager already exist or deleted");
        Manager newManager = Manager.builder()
                .userName(managerDto.getUserName())
                .password(new BCryptPasswordEncoder().encode(managerDto.getPassword()))
                .personalNumber(managerDto.getPersonalNumber())
                .deleted(false)
                .build();

        managerRepository.save(newManager);

        return managerConvertor.convert(newManager);
    }

    @Override
    public List<ManagerDto> getAllManagers() {
        return managerRepository.findAllByDeleted(false).stream()
                .map(managerConvertor::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteManager(Integer managerId) {
        Manager manager = managerRepository.findById(managerId).orElseThrow(() -> new ManagerNotFound("Manager is not found"));
        manager.setDeleted(true);
        managerRepository.save(manager);
    }
}
