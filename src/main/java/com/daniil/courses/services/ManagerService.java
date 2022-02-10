package com.daniil.courses.services;

import com.daniil.courses.dto.ManagerOrderDto;
import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.ManagerUserDto;

import java.util.List;

public interface ManagerService {
    /**
     * Добавление нового товара
     */
    ManagerStoreItemDto addNewItem(ManagerStoreItemDto storeItemDto, Integer managerId);

    /**
     * Установка доступа к товару
     */
    void setAvailable(Integer storeItemId, boolean available, Integer managerId);

    /**
     * Получить все товары(доступные/недоступные)
     */
    List<ManagerStoreItemDto> viewAllStoreItems();

    /**
     * Отредактировать товар
     */
    ManagerStoreItemDto refactorStoreItem(Integer storeItemId, ManagerStoreItemDto storeItemDto, Integer managerId);

    /**
     * Изменить статус заказа
     */
    void setOrderStatus(Integer orderId, Integer managerId);

    /**
     * Получить все заказы пклиента
     */
    List<ManagerOrderDto> getAllUserOrders(Integer userId);

    /**
     * Получить все заказы
     */
    List<ManagerOrderDto> getAllOrders();

    /**
     * Получить всех клиентов
     */
    List<ManagerUserDto> getAllUsers();

    /**
     * Заблокировать клиента
     */
    void blockUser(Integer userId);

    /**
     * Разблокировать клиента
     */
    void unlockUser(Integer userId);

}
