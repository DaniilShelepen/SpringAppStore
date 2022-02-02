package com.daniil.courses.mappers;

import com.daniil.courses.dto.ManagerStoreItemDto;
import com.daniil.courses.dto.UserStoreItemDto;
import com.daniil.courses.dal.entity.StoreItem;

public interface StoreItemConvertor {
     ManagerStoreItemDto convertForManager(StoreItem storeItem);

     UserStoreItemDto convertForUser(StoreItem storeItem);
}
