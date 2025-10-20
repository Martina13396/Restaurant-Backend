package com.example.restaurant.service;

import com.example.restaurant.controller.vm.UserOrderResponse;
import com.example.restaurant.controller.vm.RequestOrderVm;
import com.example.restaurant.controller.vm.ResponseOrderVm;

public interface OrderService {

    ResponseOrderVm requestOrder(RequestOrderVm requestOrderVm);

   UserOrderResponse getOrdersByAccountId(long accountId , int page, int size);

   UserOrderResponse getAllOrders(int page, int size);
}
