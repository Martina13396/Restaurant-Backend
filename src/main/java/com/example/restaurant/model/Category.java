package com.example.restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private String name;

    private  String logo;

    private  String flag;

    @OneToMany(mappedBy = "category")
    List<Product> products = new ArrayList<>();
}
