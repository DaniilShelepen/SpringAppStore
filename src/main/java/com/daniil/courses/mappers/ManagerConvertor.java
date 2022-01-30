package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.role_models.Manager;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ManagerConvertor {
    private final ModelMapper mapper;

    public ManagerConvertor() {
        this.mapper = new ModelMapper();
    }

    public ManagerDto convert(Manager manager) {
        manager.setPassword(null);
        return mapper.map(manager, ManagerDto.class);
    }
}
