package com.example.restaurant.mapper;

import com.example.restaurant.model.ProductDetails;
import com.example.restaurant.service.dto.ProductDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductDetailsMapper {

    ProductDetailsDto toProductDetailsDto(ProductDetails productDetails);
    ProductDetails toProductDetails(ProductDetailsDto productDetailsDto);
    List<ProductDetailsDto> toProductDetailsDtos(List<ProductDetails> productDetails);

    List<ProductDetails> toProductDetailsList(List<ProductDetailsDto> productDetailsDtos);
}
