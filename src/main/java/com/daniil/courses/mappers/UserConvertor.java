package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.ManagerUserDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.models.StoreItem;
import com.daniil.courses.role_models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConvertor {
    private final ModelMapper mapper;

    public UserConvertor() {
        this.mapper = new ModelMapper();
    }

    public ManagerUserDto convertForManager(User user) {
        user.setPassword(null);
        return mapper.map(user, ManagerUserDto.class);
    }


}
