package com.daniil.courses.models;


import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    protected String name;
    protected String description;
    protected String type;
    protected String driverConfiguration;
    protected String CPU;
    protected boolean available;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date releaseDate;


}
