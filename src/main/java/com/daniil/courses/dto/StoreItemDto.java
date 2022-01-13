package com.daniil.courses.dto;

import com.daniil.courses.models.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
public class StoreItemDto {
    protected Item item;
    protected Integer count;
    protected BigDecimal price;
    protected boolean available;

    public StoreItemDto(Item item, BigDecimal price) {
        this.item = item;
        this.price = price;
    }
}