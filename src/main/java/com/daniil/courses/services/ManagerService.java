package com.daniil.courses.services;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.ManagerUserDto;

import java.util.List;

public interface ManagerService {

    ManagerStoreItemDto addNewItem(ManagerStoreItemDto storeItemDto, Integer managerId);

    void setAvailable(Integer storeItemId, boolean available, Integer managerId);

    List<ManagerStoreItemDto> viewAllStoreItems();

    ManagerStoreItemDto refactorStoreItem(Integer storeItemId, ManagerStoreItemDto storeItemDto, Integer managerId);

    String setOrderStatus(String externalId, Integer managerId);

    List<ManagerOrderDto> getAllUserOrders(Integer userId);

    List<ManagerOrderDto> getAllOrders();

    List<ManagerUserDto> getAllUsers();

    String blockUser(Integer userId);

    String unlockUser(Integer userId);

}
