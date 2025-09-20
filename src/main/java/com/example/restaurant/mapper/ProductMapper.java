package com.example.restaurant.mapper;


import com.example.restaurant.model.Product;
import com.example.restaurant.service.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface ProductMapper {

    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);


Product toProduct(ProductDto productDto);


ProductDto toProductDto(Product product);

List<Product> toProductList(List<ProductDto> productDtos);

List<ProductDto> toProductDtos(List<Product> products);


}
