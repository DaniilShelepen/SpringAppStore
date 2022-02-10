package com.daniil.courses.services;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserStoreItemDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FilterService {

    /**
     * Фильтр товаров
     */
    List<UserStoreItemDto> filter(String name, String description, String type,
                                  String driverConfiguration, String CPU, LocalDate releaseDate, BigDecimal price);

    /**
     * Сначало товары с минимальной ценой
     */
    List<UserStoreItemDto> getCheap();

    /**
     * Сначало товары с максимальной ценой
     */
    List<UserStoreItemDto> getExpensive();

    //я честно хз зачем я их сделал, они немного не логичные, но пускай будут уже

    /**
     * Фильтры по закзам клиента
     */
    List<ManagerOrderDto> filterUserOrderByStatus(Integer userId, String orderStatuses);

    List<ManagerOrderDto> filterUserOrderByDateNew(Integer userId);

    List<ManagerOrderDto> filterUserOrderByDateOld(Integer userId);

}
