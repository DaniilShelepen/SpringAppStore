package com.daniil.courses.mappers;

import com.daniil.courses.dto.BasketDto;
import com.daniil.courses.dal.entity.Basket;

public interface BasketConvertor {
     BasketDto convert(Basket basket);
}
