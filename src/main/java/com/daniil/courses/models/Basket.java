package com.daniil.courses.models;


import com.daniil.courses.role_models.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;


import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    User user;
    @ManyToOne
    @JoinColumn(name = "store_item_id")
    StoreItem storeItem;
    protected long count;
    protected BigDecimal price;

}

