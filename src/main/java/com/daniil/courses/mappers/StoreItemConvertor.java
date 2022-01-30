package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.models.StoreItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StoreItemConvertor {
    private final ModelMapper mapper;

    public StoreItemConvertor() {
        this.mapper = new ModelMapper();
    }

    public ManagerStoreItemDto convertForManager(StoreItem storeItem) {
        return mapper.map(storeItem, ManagerStoreItemDto.class);
    }

    public UserStoreItemDto convertForUser(StoreItem storeItem) {
        return mapper.map(storeItem, UserStoreItemDto.class);
    }
}
