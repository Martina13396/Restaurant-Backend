package com.example.restaurant.service.impl;

import com.example.restaurant.mapper.ChefMapper;

import com.example.restaurant.model.Chef;
import com.example.restaurant.repo.ChefRepo;
import com.example.restaurant.service.ChefService;
import com.example.restaurant.service.dto.ChefDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ChefServiceImpl implements ChefService {

    ChefRepo chefRepo;
    ChefMapper chefMapper;

    @Autowired
    public ChefServiceImpl(ChefRepo chefRepo, ChefMapper chefMapper) {
        this.chefRepo = chefRepo;
        this.chefMapper = chefMapper;
    }

    @Override
    public List<ChefDto> getAllChefs() {

        List<Chef> chefs = chefRepo.findAllByOrderByIdAsc();

        if(ObjectUtils.isEmpty(chefs)) {
            return new ArrayList<>();
        }
        return chefs.stream().map(chef ->  chefMapper.toChefDto(chef)).collect(Collectors.toList());


    }
}
