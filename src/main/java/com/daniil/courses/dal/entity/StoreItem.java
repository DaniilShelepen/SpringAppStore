package com.daniil.courses.dal.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity(name = "store_item")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class StoreItem {
    private BigDecimal price;
    private boolean available;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Item item;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "refactor_by")
    @LastModifiedBy
    private Manager manager;

}
