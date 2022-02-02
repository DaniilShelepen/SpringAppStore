package com.daniil.courses.dal.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "store_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    private String password;
    // @Pattern(value = "^1([345789])\\d{9}$")
    @NotBlank(message = "Номер телефона не может быть пустым")
    private String phoneNumber;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;
    boolean available;

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true,fetch = FetchType.LAZY)//fetch = FetchType.LAZY, //если игоря поставишь то будет ошибка
    @ToString.Exclude
    Set<Order> orders = new HashSet<>();

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, orphanRemoval = true,fetch = FetchType.EAGER)//fetch = FetchType.EAGER,
    @ToString.Exclude
    Set<Address> addresses = new HashSet<>();

    @LazyCollection(LazyCollectionOption.TRUE)
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL},orphanRemoval = true,fetch = FetchType.LAZY)// fetch = FetchType.LAZY, //если игоря поставишь то будет ошибка
    @ToString.Exclude
    Set<Basket> basket = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User that = (User) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
