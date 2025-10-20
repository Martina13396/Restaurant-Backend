package com.example.restaurant.service;

import com.example.restaurant.controller.vm.ProductResponseVm;
import com.example.restaurant.model.Product;
import com.example.restaurant.service.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface  ProductService {

  ProductResponseVm getProductsByCategoryId(Long categoryId ,int page, int siz );

    ProductDto saveProduct(ProductDto productDto);

    List<ProductDto> saveProducts(List<ProductDto> productDtos);

    ProductDto updateProduct(ProductDto productDto);

    List<ProductDto> updateProducts(List<ProductDto> productDtos);

    ProductDto deactivate(Long id);

    List<ProductDto> getAllProductsById(List<Long> ids);

   void deleteProducts(List<Long> ids);

  ProductResponseVm searchProducts(String keyWord ,int page, int size  );

  ProductResponseVm getAllProductsByIdAsc(int page , int size );

  ProductResponseVm searchByCategoryAndKeyword( Long categoryId, String keyword , int page, int size);


  ProductDto getProductById(Long id );

  Product getPersistentProductById(Long id);

  List<Product> getAllPersistentProductsByIds(List<Long> ids);

  List<ProductDto> findAll();
}
