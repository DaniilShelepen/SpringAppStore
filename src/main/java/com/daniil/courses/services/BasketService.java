package com.daniil.courses.services;

import com.daniil.courses.dto.BasketDto;

import java.util.List;

public interface BasketService {
    /**
     * получить корзину
     */
    List<BasketDto> getBasketByUser(Integer userId);

    /**
     * добавить товар в корзину
     */
    void addItemToBasketByUser(Integer storeItemId, Integer userId, Integer count);

    /**
     * удалить товар из корзины
     */
    void removeFromBasketByUser(Integer storeItemId, Integer userId);

    /**
     * очистить корзину
     */
    void clearBasketByUser(Integer userId);
}
