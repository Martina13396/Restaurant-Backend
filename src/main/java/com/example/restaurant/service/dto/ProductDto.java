package com.example.restaurant.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Product Dto" , description = "Product Dto for product table")
public class ProductDto {
    @Schema(name = "Product ID", description = "id for product" , example = "1")
    private  Long id;
    @Schema(name = "Name", description = "Product Name" , example = "Orange juice")
    @NotBlank(message = "product.name.required")
    @Size(min = 2, max = 100, message = "product.name.size")
    private  String name;
    @Schema(name = "Image Path", description = "path for product image" , example = "hot-drinks/plainsahlab.jpg")
    @NotBlank(message = "product.image.required")
    private String imagePath;
    @Schema(name = "Product Description", description = "description for the product" , example = "Sahlab With Nuts. EGP 10.00. Plain Sahlab. EGP 8.00. Cappuccino. EGP 10.00. Mocha Latte. EGP 15.00. Hot Lemon. EGP 7.00. Latte. EGP 15.00. Nescafe Milk.")
    @NotBlank(message = "product.desc.required")
    @Size(max = 2000, message = "product.desc.size")
    private  String description;
    @Schema(name = "Price", description = "Product Price" , example = "$50")
    @NotNull(message = "product.price.required")
    @DecimalMin(value = "0.0", inclusive = false, message = "product.price.min")
    private  double price;

    private boolean active = true;
    private ProductDetailsDto productDetails;

    @NotNull(message ="category.id.notnull")
    private Long categoryId;


}
