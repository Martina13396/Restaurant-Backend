package com.example.restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product  extends BaseEntity{


    private  String name;
    private String imagePath;
    @Column(length = 2000)
    private  String description;

    private  double price;

    private boolean active = true;



    @ManyToOne
    private Category category;


    @OneToOne(cascade =  CascadeType.ALL)
  private ProductDetails productDetails;

    @OneToMany(mappedBy = "product" ,  cascade = CascadeType.ALL)
   private List<OrderProduct> orderProducts = new ArrayList<>();
}
