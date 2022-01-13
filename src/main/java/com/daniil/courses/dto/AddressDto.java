package com.daniil.courses.dto;

import com.daniil.courses.role_models.User;
import lombok.Data;

import javax.persistence.*;
import java.util.stream.Collectors;

@Data
public class AddressDto {

    protected String city;
    protected String street;
    protected String base;
    protected String flat;
    protected String floor;
    protected String entrance;


    public AddressDto(String base, String city, String entrance, String flat, String floor, String street) {
        this.base = base;
        this.city = city;
        this.entrance = entrance;
        this.flat = flat;
        this.floor = floor;
        this.street = street;
    }
}
