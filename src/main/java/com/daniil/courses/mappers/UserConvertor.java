package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerUserDto;
import com.daniil.courses.dal.entity.User;
import com.daniil.courses.dto.UserDto;

public interface UserConvertor {
    ManagerUserDto convertForManager(User user);

    UserDto convertForUser(User user);
}
