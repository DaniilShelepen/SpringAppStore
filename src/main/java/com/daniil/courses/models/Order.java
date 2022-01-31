package com.daniil.courses.models;

import com.daniil.courses.role_models.Manager;
import com.daniil.courses.role_models.User;
import com.daniil.courses.services.ORDER_STATUS;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@EntityListeners(AuditingEntityListener.class)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    protected ORDER_STATUS status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate date;
    @LastModifiedDate
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
