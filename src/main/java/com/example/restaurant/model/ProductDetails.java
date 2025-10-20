package com.example.restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails extends BaseEntity {

    private String details;

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "productDetails" )
    @JoinColumn(unique = true)
    private Product product;
}
