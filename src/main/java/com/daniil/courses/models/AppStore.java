package com.daniil.courses.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Service
public class AppStore {
    private final String shopName = "AppStore";
    @OneToMany(mappedBy = "appStore", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<StoreItem> items;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



}
