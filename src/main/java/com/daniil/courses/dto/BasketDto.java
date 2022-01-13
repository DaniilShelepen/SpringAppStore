package com.daniil.courses.dto;

import com.daniil.courses.models.StoreItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BasketDto {
    StoreItem storeItem;
    protected long count;
    protected BigDecimal price;
}
