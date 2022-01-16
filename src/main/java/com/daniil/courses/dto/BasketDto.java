package com.daniil.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BasketDto {
    ItemDto ItemDto;
    protected long count;
    protected BigDecimal price;
}
