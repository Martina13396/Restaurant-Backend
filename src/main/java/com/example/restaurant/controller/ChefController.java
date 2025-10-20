package com.example.restaurant.controller;

import com.example.restaurant.helper.BundleMessage;
import com.example.restaurant.service.ChefService;
import com.example.restaurant.service.dto.ChefDto;
import com.example.restaurant.service.dto.ExceptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(
        name= "Chef Controller",
        description = "get all chefs"
)
@RestController
@RequestMapping("/api/chef")

public class ChefController {

    ChefService chefService;

    @Autowired
    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get all chefs"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Http Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            )
    })
    @GetMapping("/getAll")
    ResponseEntity<List<ChefDto>> getAllChefs(){
        return ResponseEntity.ok(chefService.getAllChefs());
    }
}
