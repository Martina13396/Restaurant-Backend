package com.example.restaurant.controller.vm;

import com.example.restaurant.model.Product;
import com.example.restaurant.service.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseVm  {
    private List<ProductDto> products; // page

    private Long totalProducts;  // size  60



}
