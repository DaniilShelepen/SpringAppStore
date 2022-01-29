package com.daniil.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerStoreItemDto {
    protected Integer id;
    protected ItemDto itemDto;
    protected BigDecimal price;
    protected boolean available;
    protected ManagerDto manger;


}
