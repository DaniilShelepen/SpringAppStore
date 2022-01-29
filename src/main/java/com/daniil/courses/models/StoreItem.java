package com.daniil.courses.models;

import com.daniil.courses.role_models.Manager;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoreItem {
    protected BigDecimal price;
    protected boolean available;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Item item;
    @ManyToOne
    @JoinColumn(name = "refactor_by")
    private Manager manager;

}
