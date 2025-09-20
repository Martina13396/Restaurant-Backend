package com.example.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String name;
    private String imagePath;
    @Column(length = 1000)
    private  String description;
    private  double price;

    @ManyToOne
    @JsonIgnore
    private Category category;

    @ManyToMany(mappedBy = "products")
   private List<Order> orders;
}
