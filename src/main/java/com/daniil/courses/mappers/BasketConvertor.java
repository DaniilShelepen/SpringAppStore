package com.daniil.courses.mappers;

import com.daniil.courses.dto.BasketDto;
import com.daniil.courses.models.Basket;
import org.springframework.stereotype.Component;

@Component
public class BasketConvertor {
    private final ItemConvertor itemConvertor;

    public BasketConvertor(ItemConvertor itemConvertor) {
        this.itemConvertor = itemConvertor;
    }

    public BasketDto convert(Basket basket) {
        return new BasketDto(itemConvertor.convert(basket.getStoreItem().getItem())
                , basket.getCount(), basket.getPrice());
    }
}
