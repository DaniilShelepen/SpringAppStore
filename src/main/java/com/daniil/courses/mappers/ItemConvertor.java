package com.daniil.courses.mappers;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dto.ManagerDto;
import com.daniil.courses.models.Item;
import com.daniil.courses.role_models.Manager;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemConvertor {
    private final ModelMapper mapper;

    public ItemConvertor() {
        this.mapper = new ModelMapper();
    }

    public ItemDto convert(Item item) {
        return mapper.map(item, ItemDto.class);
    }
}
