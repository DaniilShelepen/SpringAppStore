package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerUserDto;
import com.daniil.courses.dal.entity.User;

public interface UserConvertor {
     ManagerUserDto convertForManager(User user);

}
