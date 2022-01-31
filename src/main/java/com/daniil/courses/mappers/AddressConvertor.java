package com.daniil.courses.mappers;

import com.daniil.courses.dto.AddressDto;
import com.daniil.courses.models.Address;

public interface AddressConvertor {
    AddressDto convert(Address address);
}
