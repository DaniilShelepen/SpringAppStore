package com.daniil.courses.services;

import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.role_models.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface FilterService {


    List<UserStoreItemDto> getAllItemsWithType(String type);

    List<UserStoreItemDto> getAllItemsWithDriverConfiguration(String configuration);

    List<UserStoreItemDto> getAllItemsWithCPU(String CPU);

    List<UserStoreItemDto> getAllWithReleaseDate(LocalDate date);//с этого момента и дальше




    List<UserOrderDto> filterUserOrderByStatus(Integer userId,  String orderStatuses);

    List<UserOrderDto> filterUserOrderByDateNew(Integer userId);

    List<UserOrderDto> filterUserOrderByDateOld(Integer userId);

}
