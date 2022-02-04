package com.daniil.courses.dal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)//fetch = FetchType.LAZY, //если игоря поставишь то будет ошибка
    List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)//fetch = FetchType.EAGER,
    List<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)// fetch = FetchType.LAZY, //если игоря поставишь то будет ошибка
    List<Basket> basket;

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
