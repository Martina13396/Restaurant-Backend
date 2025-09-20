package com.example.restaurant.controller;

import com.example.restaurant.helper.BundleMessage;
import com.example.restaurant.service.CategoryService;
import com.example.restaurant.service.dto.CategoryDto;
import com.example.restaurant.service.dto.ExceptionDto;
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

import java.util.List;
import java.util.Objects;
@Tag(
        name = "Category Controller",
        description = "management for category apis"
)
@RestController
@RequestMapping("/api/categories")
//@CrossOrigin("http://localhost:4200")
public class CategoryController {

    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "api to get all categories"
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
            @ApiResponse(
                    responseCode = "404",
                    description = "Http Status Not Found",
                    content = @Content(
                            schema = @Schema(implementation =ExceptionDto.class)
                    )
            )
    })

    @GetMapping("/getAll")
    ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategoriesByNameAsc());

    }

    @Operation(
          summary = "api to create a category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status create category"
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

    ResponseEntity<CategoryDto> saveCategory(@Valid @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.saveCategory(categoryDto));

    }

    @Operation(
            summary = "api to create list of categories "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status create category"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http Status internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ExceptionDto.class)
                    )
            ),
    })
    @PostMapping("/create/list")
   ResponseEntity< List<CategoryDto> >saveCategories(@Valid List<CategoryDto> categoryDtos){
        return ResponseEntity.ok(categoryService.saveCategories(categoryDtos));
    }

    @Operation(
            summary = "api to update a category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status create category"
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
   ResponseEntity< CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto));
    }

   @Operation(
           summary = "api to update list of categories"
   )
   @ApiResponses({
           @ApiResponse(
                   responseCode = "200",
                   description = "Http Status create category"
           ),
           @ApiResponse(
                   responseCode = "500",
                   description = "Http Status internal server error",
                   content = @Content(
                           schema = @Schema(implementation = ExceptionDto.class)
                   )
           ),
   })
    @PutMapping("updateList")
    ResponseEntity<List<CategoryDto>> updateCategories(@Valid @RequestBody List<CategoryDto> categoryDtos){
        return ResponseEntity.ok(categoryService.updateCategories(categoryDtos));
    }

  @Operation(
        summary = "api to delete a category by its id"
)
      @DeleteMapping("/deleteOne")
    ResponseEntity<Void> deleteCateGoryById(@RequestParam Long id){
        if(Objects.nonNull(id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }
   @Operation(
           summary = "api to delete list of categories by their ids"
   )
    @DeleteMapping("/deleteList")
  ResponseEntity< Void> deleteCategoriesByIds(@RequestParam List<Long> ids){
        if(Objects.nonNull(ids)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
  }
}
