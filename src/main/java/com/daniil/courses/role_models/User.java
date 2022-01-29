package com.daniil.courses.role_models;

import com.daniil.courses.models.Address;
import com.daniil.courses.models.Basket;
import com.daniil.courses.models.Order;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "store_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    protected String name;
    protected String surname;
    protected String password;
    // @Pattern(value = "^1([345789])\\d{9}$")
    @NotBlank(message = "Номер телефона не может быть пустым")
    protected String phoneNumber;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    protected LocalDate birthday;
    boolean available;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<Basket> basket;

}
