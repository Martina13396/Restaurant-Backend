package com.example.restaurant.service;

import com.example.restaurant.service.dto.ProductDetailsDto;
import com.example.restaurant.service.dto.ProductDto;

public interface ProductDetailsService {

    ProductDetailsDto getProductDetailsDtoByProductId(Long productId);


}
