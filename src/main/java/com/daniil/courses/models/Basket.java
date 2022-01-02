package com.daniil.courses.models;


import com.daniil.courses.role_models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
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
//    @OneToMany//(mappedBy = "basket", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
//    List<StoreItem> storeItem;

    //@ManyToOne
    @ManyToOne
    @JoinColumn(name = "store_item_id")
    StoreItem storeItem;

    protected long count;



}

