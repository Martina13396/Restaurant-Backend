package com.example.restaurant.controller.vm;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseOrderVm {
    @NotEmpty(message = "code.not.empty")
    private String code;
    @NotNull(message = "price.not.empty")
    private Double totalPrice;
    @NotNull(message = "total.number.not.empty")
    private Integer totalNumber;
}
