package com.example.restaurant.controller;

import com.example.restaurant.controller.vm.UserOrderResponse;
import com.example.restaurant.controller.vm.ProductResponseVm;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
                    description = "Http Status create order"
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


    @Operation(
            summary = "api to get orders by account id",
            description = "this api is to get orders under certain account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get orders by accountId",
                    content = @Content(
                            schema =  @Schema(implementation = UserOrderResponse.class)
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

    @GetMapping("/getByAccount")
    ResponseEntity<UserOrderResponse> getOrdersByAccountId(@RequestParam long accountId , @RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(orderService.getOrdersByAccountId(accountId, page, size));
    }

    @Operation(
            summary = "api to search products by id",
            description = "this api is to get all orders "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http Status get orders",
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


    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserOrderResponse> getAllOrders(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(orderService.getAllOrders(page, size));
    }
}
