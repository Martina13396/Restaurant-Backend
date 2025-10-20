package com.example.restaurant.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ProductDetails Dto" , description = "Product details Dto for product details table")
public class ProductDetailsDto {
    @Schema(name = "Product ID", description = "id for product" , example = "1")
    private Long id;

    @Schema(name = "Product Details", description = "details for the product" , example = "this product is made by chef Ahmed , it's a main dish and it takes 30 mins to be prepared")
    @NotBlank(message = "product.details.required")
    @Size(max = 1000, message = "product.details.size")
    private String details;


}
