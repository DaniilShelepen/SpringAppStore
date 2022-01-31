package com.daniil.courses.mappers;

import com.daniil.courses.dto.ItemDto;
import com.daniil.courses.models.Item;

public interface ItemConvertor {
     ItemDto convert(Item item);


}
