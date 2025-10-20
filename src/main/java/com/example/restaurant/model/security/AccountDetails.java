package com.example.restaurant.model.security;

import com.example.restaurant.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails extends BaseEntity {

    private Integer age;
    private String phoneNumber;
    private String address;

    @OneToOne(fetch = FetchType.EAGER)
    private Account account;
}
