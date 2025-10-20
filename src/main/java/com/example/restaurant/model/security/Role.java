package com.example.restaurant.model.security;

import com.example.restaurant.model.BaseEntity;
import com.example.restaurant.model.RoleCode;
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
public class Role extends BaseEntity {


    @Enumerated(EnumType.STRING)
    private RoleCode code;

    @ManyToMany(mappedBy = "roles" , cascade = CascadeType.ALL)
    List<Account> accounts;


}
