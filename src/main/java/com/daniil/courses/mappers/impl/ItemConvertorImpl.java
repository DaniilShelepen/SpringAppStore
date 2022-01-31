package com.daniil.courses.mappers.impl;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.mappers.ItemConvertor;
import com.daniil.courses.models.Item;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemConvertorImpl implements ItemConvertor {
    private final ModelMapper mapper;

    public ItemConvertorImpl() {
        this.mapper = new ModelMapper();
    }

    public ItemDto convert(Item item) {
        return mapper.map(item, ItemDto.class);
    }
}
