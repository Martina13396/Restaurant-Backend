package com.example.restaurant.mapper;


import com.example.restaurant.model.Category;
import com.example.restaurant.service.dto.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);

    List<Category> toCategoryList(List<CategoryDto> categoryDtos);

    List<CategoryDto> toCategoryDtos(List<Category> categorys);
}
