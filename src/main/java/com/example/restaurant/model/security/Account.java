package com.example.restaurant.model.security;

import com.example.restaurant.model.BaseEntity;
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
public class Account extends BaseEntity {

     @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private boolean isDeleted = false;

    @OneToOne(mappedBy = "account" , cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private AccountDetails accountDetails;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Account_Roles",
            joinColumns = @JoinColumn(name = "accounts_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "account" , cascade = CascadeType.ALL)
    List<ContactInfo> contactInfos;

    @OneToMany(mappedBy = "account" , cascade = CascadeType.ALL)
    private List<Order> orders;
}
