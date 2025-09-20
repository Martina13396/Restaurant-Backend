package com.example.restaurant.controller;

import com.example.restaurant.controller.vm.RequestOrderVm;
import com.example.restaurant.controller.vm.ResponseOrderVm;
import com.example.restaurant.service.OrderService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Tag(
        name = "Order Controller",
        description = "to get order"
)
@RestController
@RequestMapping("orders")
public class OrderController {
 private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "api to create order"
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
                            schema = @Schema(implementation = ExceptionDto.class )
                    )
            ),

    })

    @PostMapping("createOrder")
    ResponseEntity<ResponseOrderVm> requestOrder(@Valid @RequestBody RequestOrderVm requestOrderVm) {
        return ResponseEntity.created(URI.create("create-orders")).body(orderService.requestOrder(requestOrderVm));

    }
}
