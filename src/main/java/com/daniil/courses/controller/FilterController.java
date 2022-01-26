package com.daniil.courses.controller;

import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.services.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/filter/")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;

   @GetMapping("type/{type}")
    public List<UserStoreItemDto> filterType(@PathVariable String type) {
        return filterService.getAllItemsWithType(type);
    }

    @GetMapping("config/{configuration}")
    public List<UserStoreItemDto> filterConfiguration(@PathVariable String configuration) {
        return filterService.getAllItemsWithDriverConfiguration(configuration);
    }

    @GetMapping("cpu/{CPU}")
    public List<UserStoreItemDto> filterCPU(@PathVariable String CPU) {
        return filterService.getAllItemsWithCPU(CPU);
    }

    @GetMapping("date/{date}")
    public List<UserStoreItemDto> filterDate(@PathVariable LocalDate date) {
        return filterService.getAllWithReleaseDate(date);
    }

    @GetMapping("{userId}/{orderStatuses}")
    public List<UserOrderDto> filterUserStatus(@PathVariable String orderStatuses, @PathVariable Integer userId) {
        return filterService.filterUserOrderByStatus(userId, orderStatuses);
    }


    @GetMapping("{userId}/new")
    public List<UserOrderDto> filterOrderNew(@PathVariable Integer userId) {
        return filterService.filterUserOrderByDateNew(userId);
    }

    @GetMapping("{userId}/old")
    public List<UserOrderDto> filterOrderOld(@PathVariable Integer userId) {
        return filterService.filterUserOrderByDateOld(userId);
    }
}
