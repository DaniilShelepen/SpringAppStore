package com.daniil.courses.mappers.impl;

import com.daniil.courses.dto.BasketDto;
import com.daniil.courses.mappers.BasketConvertor;
import com.daniil.courses.dal.entity.Basket;
import org.springframework.stereotype.Component;

@Component
public class BasketConvertorImpl implements BasketConvertor {
    private final ItemConvertorImpl itemConvertorImpl;

    public BasketConvertorImpl(ItemConvertorImpl itemConvertorImpl) {
        this.itemConvertorImpl = itemConvertorImpl;
    }

    public BasketDto convert(Basket basket) {
        return new BasketDto(itemConvertorImpl.convert(basket.getStoreItem().getItem())
                , basket.getCount(), basket.getPrice());
    }
}
