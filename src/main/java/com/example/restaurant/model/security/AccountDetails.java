package com.example.restaurant.model.security;

import jakarta.persistence.*;

@Entity
public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer age;
    private String phoneNumber;
    private String address;

    @OneToOne
    private Account account;
}
