package com.example.restaurant.service.impl;

import com.example.restaurant.mapper.ProductDetailsMapper;
import com.example.restaurant.mapper.ProductMapper;
import com.example.restaurant.model.Product;
import com.example.restaurant.model.ProductDetails;
import com.example.restaurant.repo.ProductDetailsRepo;
import com.example.restaurant.repo.ProductRepo;
import com.example.restaurant.service.ProductDetailsService;
import com.example.restaurant.service.dto.ProductDetailsDto;
import com.example.restaurant.service.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements ProductDetailsService {

   ProductRepo productRepo;
    ProductDetailsRepo productDetailsRepo;
    ProductDetailsMapper productDetailsMapper;
    ProductMapper productMapper;


    @Autowired
    public ProductDetailServiceImpl(ProductDetailsRepo productDetailsRepo, ProductDetailsMapper productDetailsMapper, ProductRepo productRepo , ProductMapper productMapper) {
        this.productDetailsRepo = productDetailsRepo;
        this.productDetailsMapper = productDetailsMapper;
        this.productRepo = productRepo;
        this.productMapper = productMapper;
    }

    @Override
    @Cacheable(value = "productDetails" , key = "#productId")
    public ProductDetailsDto getProductDetailsDtoByProductId(Long productId) {
        Optional<ProductDetails> productDetails = productDetailsRepo.findByProductId(productId);
        if (!productDetails.isPresent()) {
            throw new RuntimeException("productDetails.not.found");
        }

        return productDetailsMapper.toProductDetailsDto(productDetails.get());

    }



}
