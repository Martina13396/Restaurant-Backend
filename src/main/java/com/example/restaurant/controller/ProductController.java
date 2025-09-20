package com.example.restaurant.controller;

import com.example.restaurant.controller.vm.ProductResponseVm;
import com.example.restaurant.helper.BundleMessage;
import com.example.restaurant.service.ProductService;
import com.example.restaurant.service.dto.ExceptionDto;
import com.example.restaurant.service.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Tag(
        name = "Product Controller",
        description = "apis management for products"
)
@RestController

@RequestMapping("/api/products")

//@CrossOrigin("http://localhost:4200")
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
      @Operation(
              summary = "api to search products by id",
              description = "this api is to get products under certain category"
      )
      @ApiResponses({
              @ApiResponse(
                      responseCode = "200",
                      description = "Http Status get all categories",
                      content = @Content(
                              schema =  @Schema(implementation = ProductResponseVm.class)
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



    @GetMapping("/searchByCategoryId/{categoryId}")
   ResponseEntity<ProductResponseVm> getProductsByCategoryId(@PathVariable Long categoryId , @Parameter(description = "this page contain products and must start with 1") @RequestParam int page,@Parameter(description = "this is size of products") @RequestParam int size){
         return  ResponseEntity.ok(productService.getProductsByCategoryId(categoryId , page ,size));
    }

   @Operation(
           summary = "api to save a product"
   )
   @ApiResponses({
           @ApiResponse(
                   responseCode = "200",
                   description = "Http Status get all categories",
                   content = @Content(
                           schema = @Schema(implementation = ProductResponseVm.class)
                   )

           ),
           @ApiResponse(
                   responseCode = "500",
                   description = "Http Status internal server error",
                   content = @Content(
                           schema = @Schema(implementation = ExceptionDto.class)
                   )
           ),

   })
    @PostMapping("/create")
   ResponseEntity< ProductDto> saveProduct(@Valid @RequestBody ProductDto productDto){
         return  ResponseEntity.ok(productService.saveProduct(productDto));
    }

   @Operation(
           summary = "api to create list of products"
   )
   @ApiResponses({
           @ApiResponse(
                   responseCode = "200",
                   description = "Http Status get all categories"
           ),
           @ApiResponse(
                   responseCode = "500",
                   description = "Http Status internal server error",
                   content = @Content(
                           schema = @Schema(implementation = ExceptionDto.class)
                   )
           ),

   })
    @PostMapping("createList")
   ResponseEntity< List<ProductDto>> saveProducts(@Valid @RequestBody List<ProductDto> productDtos){
        return  ResponseEntity.ok(productService.saveProducts(productDtos));
    }

    @Operation(
            summary = "api to update a product"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get all categories"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),

    })
    @PutMapping("/update")
    ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto){

        return ResponseEntity.ok(productService.updateProduct(productDto));
    }

    @Operation(
            summary = "api to update list of products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get all categories"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),

    })
    @PutMapping("/updateList")
    ResponseEntity<List<ProductDto>> updateProducts(@Valid @RequestBody List<ProductDto> productDtos){
        return  ResponseEntity.ok(productService.updateProducts(productDtos));
    }

    @Operation(
            summary = "api to delete a product"
    )
    @DeleteMapping("deleteOne")
    ResponseEntity<Void> deleteProduct(@RequestParam Long id){
        if(Objects.nonNull(id)){
          return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "api to delete list of products"
    )
    @DeleteMapping("deleteList")
    ResponseEntity<Void> deleteProducts(@RequestParam List<Long> ids){
        if(Objects.nonNull(ids)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(
            summary = "api to search products by certain keyword"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get all categories",
                    content =  @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
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
    @GetMapping("/search")
    ResponseEntity<ProductResponseVm> searchProducts(@RequestParam String keyword,  @Parameter(description = "this page contain products and must start with 1")@RequestParam int page, @Parameter(description = "this is page size") @RequestParam int size ){
        return ResponseEntity.ok(productService.searchProducts(keyword ,page,size));
    }

    @Operation(
            summary = "api to get all products"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get all categories",
                    content =  @Content(
                            schema = @Schema(implementation = ProductResponseVm.class)
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
    @GetMapping("/getAll")
    ResponseEntity<ProductResponseVm> getAllProducts(@RequestParam  @Parameter(description = "this page contain products and must start with 1")int page,@Parameter(description = "this is page size") @RequestParam int size){
        return ResponseEntity.ok(productService.getAllProductsByIdAsc(page ,  size));
    }


   @Operation(
           summary = "api to search products in certain category"
   )
   @ApiResponses({
           @ApiResponse(
                   responseCode = "200",
                   description = "Http Status get all categories",
                   content =   @Content(
                           schema = @Schema(implementation = ProductResponseVm.class)
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
    @GetMapping("/searchByCategoryAndKey")
   ResponseEntity<ProductResponseVm> searchByCategoryAndKeyword(@RequestParam Long categoryId , @RequestParam String keyword ,  @Parameter(description = "this page contain products and must start with 1")@RequestParam int page, @Parameter(description ="this is page size" )@RequestParam int size){

        return ResponseEntity.ok(productService.searchByCategoryAndKeyword(categoryId, keyword , page , size));
    }

}
