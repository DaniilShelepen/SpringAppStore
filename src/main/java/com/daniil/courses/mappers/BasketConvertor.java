package com.daniil.courses.mappers;

import com.daniil.courses.dto.AddressDto;
import com.daniil.courses.dto.BasketDto;
import com.daniil.courses.models.Address;
import com.daniil.courses.models.Basket;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BasketConvertor {
    private final ModelMapper mapper;

    public BasketConvertor() {
        this.mapper = new ModelMapper();
    }

    public BasketDto convert(Basket basket) {
        return mapper.map(basket, BasketDto.class);
    }
}
