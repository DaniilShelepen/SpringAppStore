package com.daniil.courses.mappers;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.dal.entity.Item;

public interface ItemConvertor {
     ItemDto convert(Item item);


}
