package com.example.restaurant.controller.vm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderVm {
    @Schema(name = "Total Price", description = "order total price", example = "$280")
    @NotNull(message = "price.not.empty")
    private Double totalPrice;
    @NotNull(message = "total.number.not.empty")
    @Schema(name = "Total Number", description = " total items number", example = "6")
    private Integer totalNumber;

    @NotEmpty(message = "products.not.empty")
    private List<ProductOrderRequestVm> products;






}
