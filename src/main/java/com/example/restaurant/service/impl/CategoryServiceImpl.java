package com.example.restaurant.service.impl;

import com.example.restaurant.mapper.CategoryMapper;
import com.example.restaurant.model.Category;
import com.example.restaurant.repo.CategoryRepo;
import com.example.restaurant.service.CategoryService;
import com.example.restaurant.service.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryMapper categoryMapper;
    CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepo categoryRepo) {
        this.categoryMapper = categoryMapper;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<CategoryDto> getAllCategoriesByNameAsc() {
        List<Category> categories = categoryRepo.findAllByOrderByNameAsc();

        if (ObjectUtils.isEmpty(categories)) {
            return new ArrayList<>();
        }

        return categories.stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
       if(Objects.nonNull(categoryDto.getId())) {
           throw new RuntimeException("id.not.null");
       }
        Category category = categoryMapper.toCategory(categoryDto);
        Category saved = categoryRepo.save(category);

        return categoryMapper.toCategoryDto(saved);
    }

    @Override
    public List<CategoryDto> saveCategories(List<CategoryDto> categoryDtos) {
        for (CategoryDto categoryDto : categoryDtos) {
            if(Objects.nonNull(categoryDto.getId())) {
                throw new RuntimeException("id.not.null");
            }
        }
        List<Category> categories = categoryMapper.toCategoryList(categoryDtos);
        List<Category> saved = categoryRepo.saveAll(categories);
        return saved.stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {

        if(Objects.isNull(categoryDto.getId())) {

            throw new RuntimeException("id.null");
        }
        Category category = categoryMapper.toCategory(categoryDto);
        Category updated = categoryRepo.save(category);
        return  categoryMapper.toCategoryDto(updated);
    }

    @Override
    public List<CategoryDto> updateCategories(List<CategoryDto> categoryDtos) {

        for (CategoryDto categoryDto : categoryDtos) {
            if(Objects.isNull(categoryDto.getId())) {
                throw new RuntimeException("id.null");
            }
        }
        List<Category> categories = categoryMapper.toCategoryList(categoryDtos);
        List<Category> updated = categoryRepo.saveAll(categories);
        return updated.stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCateGoryById(Long id) {

        categoryRepo.deleteById(id);
    }

    @Override
    public void deleteCategoriesByIds(List<Long> ids) {

        categoryRepo.deleteAllById(ids);
    }
}
