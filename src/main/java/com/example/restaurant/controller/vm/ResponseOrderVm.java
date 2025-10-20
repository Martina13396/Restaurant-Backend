package com.example.restaurant.controller.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    private String accountName;



    private String createdAt;

    private List<ProductOrderVm> productOrders;


}
