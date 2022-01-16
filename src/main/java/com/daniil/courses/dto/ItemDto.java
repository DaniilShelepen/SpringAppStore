package com.daniil.courses.dto;

import com.daniil.courses.models.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ItemDto {
    protected String name;
    protected String description;
    protected String type;
    protected String driverConfiguration;
    protected String CPU;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date releaseDate;


    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getName(), item.getDescription(), item.getType(), item.getDriverConfiguration(), item.getCPU(), item.getReleaseDate());
    }
}
