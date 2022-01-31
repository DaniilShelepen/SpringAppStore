package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.models.StoreItem;
import org.springframework.stereotype.Component;

@Component
public class StoreItemConvertor {
    private final ItemConvertor itemConvertor;
    private final ManagerConvertor managerConvertor;

    public StoreItemConvertor(ItemConvertor itemConvertor, ManagerConvertor managerConvertor) {
        this.itemConvertor = itemConvertor;
        this.managerConvertor = managerConvertor;
    }

    public ManagerStoreItemDto convertForManager(StoreItem storeItem) {
        return new ManagerStoreItemDto(storeItem.getId(), itemConvertor.convert(storeItem.getItem()),
                storeItem.getPrice(),
                storeItem.isAvailable(),
                managerConvertor.convert(storeItem.getManager()));
    }

    public UserStoreItemDto convertForUser(StoreItem storeItem) {
        return new UserStoreItemDto(storeItem.getId(), itemConvertor.convert(storeItem.getItem()), storeItem.getPrice());
    }
}
