package com.daniil.courses.mappers.impl;

import com.daniil.courses.dto.AddressDto;
import com.daniil.courses.mappers.AddressConvertor;
import com.daniil.courses.dal.entity.Address;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressConvertorImpl implements AddressConvertor {
    private final ModelMapper mapper;

    public AddressConvertorImpl() {
        this.mapper = new ModelMapper();
    }

    @Override
    public AddressDto convert(Address address) {
        return mapper.map(address, AddressDto.class);
    }

}
