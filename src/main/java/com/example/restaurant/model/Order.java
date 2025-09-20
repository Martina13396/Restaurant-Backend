package com.example.restaurant.model;

import com.example.restaurant.model.security.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Double totalPrice;

    private Integer totalNumber;

    @ManyToMany
    private List<Product> products;

    @ManyToOne
    private Account account;
}
