package com.example.restaurant.mapper;

import com.example.restaurant.model.Chef;
import com.example.restaurant.service.dto.ChefDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChefMapper {

    ChefDto toChefDto(Chef chef);

    Chef toChef(ChefDto chefDto);

    List<Chef> toChefList(List<ChefDto> chefDtos);

    List<ChefDto> toChefDtos(List<Chef> chefs);
}
