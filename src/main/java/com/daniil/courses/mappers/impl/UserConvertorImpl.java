package com.daniil.courses.mappers.impl;

import com.daniil.courses.dto.ManagerUserDto;
import com.daniil.courses.dto.UserDto;
import com.daniil.courses.mappers.UserConvertor;
import com.daniil.courses.dal.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConvertorImpl implements UserConvertor {
    private final ModelMapper mapper;

    public UserConvertorImpl() {
        this.mapper = new ModelMapper();
    }

    public ManagerUserDto convertForManager(User user) {
        user.setPassword(null);
        return mapper.map(user, ManagerUserDto.class);
    }

    @Override
    public UserDto convertForUser(User user) {
        user.setPassword(null);
        return mapper.map(user, UserDto.class);    }


}
