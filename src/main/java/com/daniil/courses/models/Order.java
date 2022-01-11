package com.daniil.courses.models;

import com.daniil.courses.role_models.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity(name = "order_entity")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    protected String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected Date dateOfRefactoring;
    protected BigDecimal price;
    @ManyToOne
    protected Address address;
    @ManyToOne(fetch = FetchType.EAGER)
    protected User user;

}
