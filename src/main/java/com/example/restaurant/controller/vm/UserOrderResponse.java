package com.example.restaurant.controller.vm;

import com.example.restaurant.service.dto.OrderDto;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderResponse {
    List<ResponseOrderVm> orders;
     private Long totalElements;

     private Double totalPrice;


}
