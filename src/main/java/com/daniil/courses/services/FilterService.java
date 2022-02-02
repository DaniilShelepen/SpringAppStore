package com.daniil.courses.services;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserStoreItemDto;

import java.time.LocalDate;
import java.util.List;

public interface FilterService {
    /**
     * Фильтр по типу
     */
    List<UserStoreItemDto> getAllItemsWithType(String type);

    /**
     * Фильтр по конфигурации
     */
    List<UserStoreItemDto> getAllItemsWithDriverConfiguration(String configuration);

    /**
     * Фильтр по описанию
     */
    List<UserStoreItemDto> getAllItemsWithDescription(String configuration);

    /**
     * Фильтр по цпу
     */
    List<UserStoreItemDto> getAllItemsWithCPU(String CPU);

    /**
     * Фильтр по дате выхода
     */
    List<UserStoreItemDto> getAllWithReleaseDate(LocalDate date);//с этого момента и дальше

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
