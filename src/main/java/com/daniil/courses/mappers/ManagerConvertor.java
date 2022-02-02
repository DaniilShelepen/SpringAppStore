package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.dal.entity.Manager;

public interface ManagerConvertor {
     ManagerDto convert(Manager manager);
}
