package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerUserDto;
import com.daniil.courses.role_models.User;

public interface UserConvertor {
     ManagerUserDto convertForManager(User user);

}
