package com.daniil.courses.dto;

import com.daniil.courses.role_models.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerStoreItemDto {
    protected Integer id;
    protected ItemDto itemDto;
    protected BigDecimal price;
    protected boolean available;
    protected Manager manger;


}
