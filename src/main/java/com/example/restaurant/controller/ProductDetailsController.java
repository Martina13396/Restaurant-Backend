package com.example.restaurant.controller;

import com.example.restaurant.controller.vm.ProductResponseVm;
import com.example.restaurant.service.ProductDetailsService;
import com.example.restaurant.service.dto.ExceptionDto;
import com.example.restaurant.service.dto.ProductDetailsDto;
import com.example.restaurant.service.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "ProductDetails Controller",
        description = "apis management for products Details"
)
@RestController
@RequestMapping("productDetails")
public class ProductDetailsController {

    ProductDetailsService productDetailsService;

    @Autowired
    public ProductDetailsController(ProductDetailsService productDetailsService) {
        this.productDetailsService = productDetailsService;
    }

    @Operation(
            summary = "api to search products by id",
            description = "this api is to get products under certain category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get product details",
                    content = @Content(
                            schema =  @Schema(implementation = ProductDetailsDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
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




    @GetMapping("/getDetails")
    ResponseEntity<ProductDetailsDto> getProductDetailsDtoById(@RequestParam Long productId){
        return ResponseEntity.ok().body(productDetailsService.getProductDetailsDtoByProductId(productId));
    }


}
