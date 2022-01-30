package com.daniil.courses.controller;

import com.daniil.courses.dto.UserOrderDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.security.AccessAdminAndManager;
import com.daniil.courses.security.AccessUser;
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

    @GetMapping("notAuthorized/type/{type}")
    public List<UserStoreItemDto> filterType(@PathVariable String type) {
        return filterService.getAllItemsWithType(type);
    }

    @GetMapping("notAuthorized/config/{configuration}")
    public List<UserStoreItemDto> filterConfiguration(@PathVariable String configuration) {
        return filterService.getAllItemsWithDriverConfiguration(configuration);
    }

    @GetMapping("notAuthorized/cpu/{CPU}")
    public List<UserStoreItemDto> filterCPU(@PathVariable String CPU) {
        return filterService.getAllItemsWithCPU(CPU);
    }

    @GetMapping("notAuthorized/date/{date}")
    public List<UserStoreItemDto> filterDate(@PathVariable LocalDate date) {
        return filterService.getAllWithReleaseDate(date);
    }

    @GetMapping("notAuthorized/cheap")
    public List<UserStoreItemDto> filterCheap() {
        return filterService.getCheap();
    }

    @GetMapping("notAuthorized/expensive")
    public List<UserStoreItemDto> filterExpensive() {
        return filterService.getExpensive();
    }

    @AccessAdminAndManager
    @GetMapping("{userId}/{orderStatuses}")
    public List<UserOrderDto> filterUserStatus(@PathVariable String orderStatuses, @PathVariable Integer userId) {
        return filterService.filterUserOrderByStatus(userId, orderStatuses);
    }

    @AccessAdminAndManager
    @GetMapping("{userId}/new")
    public List<UserOrderDto> filterOrderNew(@PathVariable Integer userId) {
        return filterService.filterUserOrderByDateNew(userId);
    }

    @AccessAdminAndManager
    @GetMapping("{userId}/old")
    public List<UserOrderDto> filterOrderOld(@PathVariable Integer userId) {
        return filterService.filterUserOrderByDateOld(userId);
    }
}
