package com.daniil.courses.services;

import com.daniil.courses.models.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ItemService {

    List<Item> getAllItems();

    List<Item> getAllItemsWithType();

    List<Item> getAllItemsWithDriverConfiguration();

    List<Item> getAllItemsWithCPU();

    Map<Item, BigDecimal> getAveragePrice();

    List<Item> getAllWithReleaseDate();

}
