package com.daniil.courses.dto;

import com.daniil.courses.models.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    protected Integer id;
    protected String city;
    protected String street;
    protected String base;
    protected String flat;
    protected String floor;
    protected String entrance;

}
