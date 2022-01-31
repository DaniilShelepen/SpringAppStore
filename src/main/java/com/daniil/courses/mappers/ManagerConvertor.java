package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.role_models.Manager;

public interface ManagerConvertor {
     ManagerDto convert(Manager manager);
}
