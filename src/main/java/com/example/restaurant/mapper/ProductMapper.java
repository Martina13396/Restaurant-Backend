package com.example.restaurant.mapper;


import com.example.restaurant.model.Product;
import com.example.restaurant.model.ProductDetails;
import com.example.restaurant.service.dto.ProductDetailsDto;
import com.example.restaurant.service.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring" )
public interface ProductMapper {

    ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

@Mapping(target = "category" , ignore = true)
@Mapping(target = "id" , ignore = true)

Product toProduct(ProductDto productDto);

@Mapping(source = "category.id", target = "categoryId")
ProductDto toProductDto(Product product);

@Mapping(target = "category" , ignore = true)
@Mapping(target = "id" , ignore = true)

void updateProductFromDto(ProductDto productDto, @MappingTarget Product product);



List<Product> toProductList(List<ProductDto> productDtos);

List<ProductDto> toProductDtos(List<Product> products);


}
