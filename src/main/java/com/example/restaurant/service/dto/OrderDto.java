package com.example.restaurant.service.dto;

import com.example.restaurant.model.Product;
import com.example.restaurant.model.security.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OrderDto" , description = "order dto Dto for order table")
public class OrderDto {

    private long id;
    @Schema(name = "code", description = "order code" , example = "Res_1")
    private String code;
    @Schema(name = "total price", description = "order total price" , example = "500 $")
    private Double totalPrice;
    @Schema(name = "total number", description = "order total number" , example = "7")
    private Integer totalNumber;

    private String accountName;




}
