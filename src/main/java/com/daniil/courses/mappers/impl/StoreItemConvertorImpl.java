package com.daniil.courses.mappers.impl;

import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.mappers.StoreItemConvertor;
import com.daniil.courses.mappers.impl.ItemConvertorImpl;
import com.daniil.courses.mappers.impl.ManagerConvertorImpl;
import com.daniil.courses.models.StoreItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreItemConvertorImpl implements StoreItemConvertor {
    private final ItemConvertorImpl itemConvertorImpl;
    private final ManagerConvertorImpl managerConvertorImpl;


    public ManagerStoreItemDto convertForManager(StoreItem storeItem) {
        return new ManagerStoreItemDto(storeItem.getId(), itemConvertorImpl.convert(storeItem.getItem()),
                storeItem.getPrice(),
                storeItem.isAvailable(),
                managerConvertorImpl.convert(storeItem.getManager()));
    }

    public UserStoreItemDto convertForUser(StoreItem storeItem) {
        return new UserStoreItemDto(storeItem.getId(), itemConvertorImpl.convert(storeItem.getItem()), storeItem.getPrice());
    }
}
