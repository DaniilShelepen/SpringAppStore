package com.daniil.courses.mappers;

import com.daniil.courses.dto.AddressDto;
import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.models.Address;
import com.daniil.courses.models.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressConvertor {
    private final ModelMapper mapper;

    public AddressConvertor() {
        this.mapper = new ModelMapper();
    }

    public AddressDto convert(Address address) {
        return mapper.map(address, AddressDto.class);
    }

}
