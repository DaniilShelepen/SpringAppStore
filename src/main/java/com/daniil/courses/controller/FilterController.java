package com.daniil.courses.controller;

import com.daniil.courses.dal.entity.Manager;
import com.daniil.courses.dal.repositories.ManagerRepository;
import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.security.AccessAdminAndManager;
import com.daniil.courses.services.FilterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/filter/")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;
    private final ManagerRepository managerRepository;

    @GetMapping("notAuthorized/type/{type}")
    @Operation(description = "Вывод отфильтрованных товаров по типу")
    public List<UserStoreItemDto> filterType(@PathVariable String type) {
        return filterService.getAllItemsWithType(type);
    }

    @GetMapping("notAuthorized/config/{configuration}")
    @Operation(description = "Вывод отфильтрованных товаров по конфигурации")
    public List<UserStoreItemDto> filterConfiguration(@PathVariable String configuration) {
        return filterService.getAllItemsWithDriverConfiguration(configuration);
    }

    @GetMapping("notAuthorized/{description}")
    @Operation(description = "Вывод отфильтрованных товаров по описанию")
    public List<UserStoreItemDto> filterDescription(@PathVariable String description) {
        return filterService.getAllItemsWithDescription(description);
    }

    @GetMapping("notAuthorized/cpu/{CPU}")
    @Operation(description = "Вывод отфильтрованных товаров по цпу")
    public List<UserStoreItemDto> filterCPU(@PathVariable String CPU) {
        return filterService.getAllItemsWithCPU(CPU);
    }

    @GetMapping("notAuthorized/date/{date}")
    @Operation(description = "Вывод отфильтрованных товаров по дате выхода")
    public List<UserStoreItemDto> filterDate(@PathVariable LocalDate date) {
        return filterService.getAllWithReleaseDate(date);
    }

    @GetMapping("notAuthorized/cheap")
    @Operation(description = "Вывод отфильтрованных товаров по цене(мин)")
    public List<UserStoreItemDto> filterCheap() {
        return filterService.getCheap();
    }

    @GetMapping("notAuthorized/expensive")
    @Operation(description = "Вывод отфильтрованных товаров по цене(макс)")
    public List<UserStoreItemDto> filterExpensive() {
        return filterService.getExpensive();
    }

    @AccessAdminAndManager
    @GetMapping("{userId}/{orderStatuses}")
    @Operation(description = "Вывод отфильтрованных заказов у клиента по статусу")
    public List<ManagerOrderDto> filterUserStatus(@PathVariable String orderStatuses, @PathVariable Integer userId, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        return filterService.filterUserOrderByStatus(userId, orderStatuses);
    }

    @AccessAdminAndManager
    @GetMapping("{userId}/new")
    @Operation(description = "Вывод отфильтрованных заказов у клиента по дате(новые)")
    public List<ManagerOrderDto> filterOrderNew(@PathVariable Integer userId, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        return filterService.filterUserOrderByDateNew(userId);
    }

    @AccessAdminAndManager
    @GetMapping("{userId}/old")
    @Operation(description = "Вывод отфильтрованных заказов у клиента по дате(старые)")
    public List<ManagerOrderDto> filterOrderOld(@PathVariable Integer userId, Principal principal) {
        Manager manager = managerRepository.findByPersonalNumberAndDeleted(principal.getName(), false);
        if (manager == null)
            throw new UsernameNotFoundException("");
        return filterService.filterUserOrderByDateOld(userId);
    }
}
