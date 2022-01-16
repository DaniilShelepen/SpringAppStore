package com.daniil.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ManagerStoreItemDto {
    protected ItemDto itemDto;
    protected BigDecimal price;
    protected boolean available;

    public ManagerStoreItemDto(ItemDto itemDto, BigDecimal price) {
        this.itemDto = itemDto;
        this.price = price;
    }

//    public StoreItemDto(Item item, BigDecimal price) {
//        this.item = item;
//        this.price = price;
//    }

}
