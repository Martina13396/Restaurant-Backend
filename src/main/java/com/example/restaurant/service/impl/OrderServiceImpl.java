package com.example.restaurant.service.impl;

import com.example.restaurant.controller.vm.RequestOrderVm;
import com.example.restaurant.controller.vm.ResponseOrderVm;
import com.example.restaurant.mapper.AccountMapper;
import com.example.restaurant.mapper.ProductMapper;
import com.example.restaurant.model.Order;
import com.example.restaurant.repo.OrderRepo;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.ProductService;
import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ThemeResolver;

import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    private final ThemeResolver themeResolver;
    private final AccountMapper accountMapper;
    private OrderRepo orderRepo;

    private ProductService  productService;



    @Autowired
     public OrderServiceImpl(OrderRepo orderRepo , ProductService productService, ThemeResolver themeResolver, AccountMapper accountMapper) {
        this.orderRepo = orderRepo;
        this.productService = productService;
        this.themeResolver = themeResolver;
        this.accountMapper = accountMapper;
    }

    @Override
    public ResponseOrderVm requestOrder(RequestOrderVm  requestOrderVm) {



        List<ProductDto> productDtos = productService.getAllProductsById(
                requestOrderVm.getProductsIds());



       AccountDto accountDto= (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Order order = new Order();

       order.setTotalPrice(requestOrderVm.getTotalPrice());
       order.setTotalNumber(requestOrderVm.getTotalNumber());
       order.setProducts(ProductMapper.PRODUCT_MAPPER.toProductList(productDtos));
       order.setAccount(accountMapper.toAccount(accountDto));
      Order savedOrder= orderRepo.save(order);
       Long Id = savedOrder.getId();
       String code = "RES-"  + Id;
       savedOrder.setCode(code);
       savedOrder = orderRepo.save(order);
       return new ResponseOrderVm(savedOrder.getCode(),savedOrder.getTotalPrice(),savedOrder.getTotalNumber());
    }
}
