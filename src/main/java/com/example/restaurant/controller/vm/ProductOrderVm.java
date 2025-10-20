package com.example.restaurant.controller.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderVm {

    private String name;
    private String imagePath;
    private Double price;
    private Integer quantity;
    private Double subtotal;
}
