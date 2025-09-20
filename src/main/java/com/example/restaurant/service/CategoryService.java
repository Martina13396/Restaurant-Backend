package com.example.restaurant.service;

import com.example.restaurant.service.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategoriesByNameAsc();

    CategoryDto saveCategory(CategoryDto categoryDto);

    List<CategoryDto> saveCategories(List<CategoryDto> categoryDtos);

    CategoryDto updateCategory(CategoryDto categoryDto);

    List<CategoryDto> updateCategories(List<CategoryDto> categoryDtos);

   void deleteCateGoryById(Long id);

   void deleteCategoriesByIds(List<Long> ids);




}
