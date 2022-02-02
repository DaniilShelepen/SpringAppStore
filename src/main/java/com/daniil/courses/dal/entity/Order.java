package com.daniil.courses.dal.entity;

import com.daniil.courses.dto.ORDER_STATUS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
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
    private ORDER_STATUS status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
    @LastModifiedDate
    private Date dateOfRefactoring;
    private BigDecimal price;
    private String externalId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @LazyCollection(LazyCollectionOption.TRUE)
    @ManyToMany(fetch = FetchType.EAGER)//тут не ван ту мени но я и сюда пытался впихивать
    List<StoreItem> storeItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "refactor_by")
    @LastModifiedBy
    private Manager manager;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order that = (Order) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
