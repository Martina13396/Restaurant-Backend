package com.example.restaurant.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Category Dto" , description = "Category Dto for Category table")
public class CategoryDto {
    @Schema(name = "Category ID", description = "id for category" , example = "1")
    private  long id;
    @Schema(name = "Name", description = "Category Name" , example = "Fast Food")
    @NotBlank(message = "category.name.required")
    @Size(min = 2, max = 100, message = "category.name.size")
    private String name;
    @Schema(name = "Category Logo", description = "logo used with category" , example = "Ffa fa-pizza-slice")
    @NotBlank(message = "category.logo.required")
    private  String logo;

    @Schema(name = "Category Flag", description = "Flag for category" , example = "lovely")
    @NotNull(message = "category.flag.required")
    private String flag;

    List<ProductDto> products;
}
