package com.daniil.courses.models;

import com.daniil.courses.role_models.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


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
    protected String city;
    protected String street;
    protected String base;
    protected String flat;
    protected String floor;
    protected String entrance;
    protected boolean visible;

    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

}
