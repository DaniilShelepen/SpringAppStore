package com.daniil.courses.models;

import com.daniil.courses.role_models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    protected String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date date;
    @OneToOne(fetch = FetchType.EAGER)
    protected Address address;
    @OneToOne(fetch = FetchType.EAGER)
    protected User user;
}
