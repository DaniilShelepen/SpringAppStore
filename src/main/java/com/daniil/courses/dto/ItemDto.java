package com.daniil.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;
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
}
