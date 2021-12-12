package com.daniil.courses.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class AppStore {
    protected final String shopName = "AppStore";
    @OneToMany(mappedBy = "appStore", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<Item> items;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



}
