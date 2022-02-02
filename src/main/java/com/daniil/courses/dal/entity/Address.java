package com.daniil.courses.dal.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Getter
@Setter
@RequiredArgsConstructor
@SuperBuilder
@AllArgsConstructor
@ToString
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private   Integer id;
    private String city;
    private String street;
    private String base;
    private String flat;
    private String floor;
    private String entrance;
    private boolean visible;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

}
