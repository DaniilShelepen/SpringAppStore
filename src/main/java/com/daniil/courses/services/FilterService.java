package com.daniil.courses.services;

import com.daniil.courses.models.Item;
import com.daniil.courses.models.StoreItem;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FilterService {

    List<StoreItem> getAllItemsWithType(String type);

    List<StoreItem> getAllItemsWithDriverConfiguration(String configuration);

    List<StoreItem> getAllItemsWithCPU(String CPU);


    List<StoreItem> getAllWithReleaseDate(Date date);//с этого момента и дальше


}
