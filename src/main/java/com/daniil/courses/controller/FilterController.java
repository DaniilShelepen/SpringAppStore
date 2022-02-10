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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/filter/")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;
    private final ManagerRepository managerRepository;

    @GetMapping("notAuthorized/filter")
    @Operation(description = "Вывод товаров по фильтру")
    public List<UserStoreItemDto> filter(@RequestParam(required = false,defaultValue = "") String name, @RequestParam(required = false,defaultValue = "") String description,
                                         @RequestParam(required = false,defaultValue = "") String type, @RequestParam(required = false,defaultValue = "") String driverConfiguration,
                                         @RequestParam(required = false,defaultValue = "") String CPU, @RequestParam(required = false,defaultValue =  "01.01.518") LocalDate releaseDate,
                                         @RequestParam(required = false,defaultValue = "999999999") BigDecimal price) {

        return filterService.filter(name, description, type, driverConfiguration, CPU, releaseDate, price);
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
