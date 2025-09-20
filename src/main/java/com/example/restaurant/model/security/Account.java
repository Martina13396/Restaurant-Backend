package com.example.restaurant.model.security;

import com.example.restaurant.model.ContactInfo;
import com.example.restaurant.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToOne(mappedBy = "account" , cascade = CascadeType.ALL)
    private AccountDetails accountDetails;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToMany(mappedBy = "account")
    List<ContactInfo> contactInfos;

    @OneToMany(mappedBy = "account")
    private List<Order> orders;
}
