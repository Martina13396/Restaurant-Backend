package com.example.restaurant.service;

import com.example.restaurant.controller.vm.RequestOrderVm;
import com.example.restaurant.controller.vm.ResponseOrderVm;

public interface OrderService {

    ResponseOrderVm requestOrder(RequestOrderVm requestOrderVm);
}
