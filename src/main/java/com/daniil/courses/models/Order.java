package com.daniil.courses.models;

import com.daniil.courses.role_models.Manager;
import com.daniil.courses.role_models.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "order_entity")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    protected String status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate date;
    protected Date dateOfRefactoring;
    protected BigDecimal price;
    protected String externalId;
    @ManyToOne
    protected Address address;
    @ManyToOne(fetch = FetchType.EAGER)
    protected User user;
    @ManyToMany(fetch = FetchType.EAGER)
    List<StoreItem> storeItem;
    @ManyToOne
    @JoinColumn(name = "refactor_by")
    private Manager manager;
}
