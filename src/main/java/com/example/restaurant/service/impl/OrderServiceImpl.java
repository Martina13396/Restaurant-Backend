package com.example.restaurant.service.impl;

import com.example.restaurant.controller.vm.ProductOrderVm;
import com.example.restaurant.controller.vm.UserOrderResponse;
import com.example.restaurant.controller.vm.RequestOrderVm;
import com.example.restaurant.controller.vm.ResponseOrderVm;
import com.example.restaurant.mapper.AccountMapper;
import com.example.restaurant.mapper.OrderMapper;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderProduct;
import com.example.restaurant.model.Product;
import com.example.restaurant.repo.AccountRepo;
import com.example.restaurant.repo.OrderRepo;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.ProductService;
import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.dto.OrderDto;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ThemeResolver;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ThemeResolver themeResolver;
    private final AccountMapper accountMapper;
    private final AccountRepo accountRepo;
    private OrderRepo orderRepo;

    private ProductService  productService;



    @Autowired
     public OrderServiceImpl(OrderRepo orderRepo , ProductService productService, ThemeResolver themeResolver, AccountMapper accountMapper, AccountRepo accountRepo) {
        this.orderRepo = orderRepo;
        this.productService = productService;
        this.themeResolver = themeResolver;
        this.accountMapper = accountMapper;
        this.accountRepo = accountRepo;
    }

    @Override
    @CacheEvict(value ="orders" , allEntries = true )
    public ResponseOrderVm requestOrder(RequestOrderVm  requestOrderVm) {


       AccountDto accountDto= (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       Order order = new Order();
       order.setAccount(accountMapper.toAccount(accountDto));
       order.setAccountName(accountDto.getUsername());
        if (requestOrderVm.getProducts() == null || requestOrderVm.getProducts().isEmpty()) {
            throw new RuntimeException("products.not.empty");
        }
     List<OrderProduct> orderProducts = requestOrderVm.getProducts().stream().map(p ->{
         Product product = productService.getPersistentProductById(p.getProductId());
         int quantity = p.getQuantity() != null && p.getQuantity() > 0 ? p.getQuantity() : 1;
         OrderProduct orderProduct = new OrderProduct();
         orderProduct.setOrder(order);
         orderProduct.setProduct(product);
         orderProduct.setQuantity(p.getQuantity());
         orderProduct.setSubtotal(product.getPrice() * quantity);
         return orderProduct;
     }).collect(Collectors.toList());
       order.setOrderProducts(orderProducts);
       order.setAccount(accountMapper.toAccount(accountDto));
       order.setAccountName(accountDto.getUsername());
       double price = orderProducts.stream().mapToDouble(OrderProduct::getSubtotal).sum();
       int totalNumber = orderProducts.stream().mapToInt(OrderProduct::getQuantity).sum();
    order.setTotalPrice(price);
    order.setTotalNumber(totalNumber);

      Order savedOrder= orderRepo.save(order);
       Long Id = savedOrder.getId();
       String code = "RES-"  + savedOrder.getId();
       savedOrder.setCode(code);
       savedOrder = orderRepo.save(savedOrder);

     List<ProductOrderVm> productOrderVms = savedOrder.getOrderProducts().stream().map(
             orderProduct -> new ProductOrderVm(
                     orderProduct.getProduct().getName(),
                     orderProduct.getProduct().getImagePath(),
                     orderProduct.getProduct().getPrice(),
                     orderProduct.getQuantity(),
                     orderProduct.getSubtotal()

             )
     ).collect(Collectors.toList());

     return  new ResponseOrderVm(
             savedOrder.getCode(),
             savedOrder.getTotalPrice(),
             savedOrder.getTotalNumber(),
             savedOrder.getAccountName(),
             savedOrder.getCreatedAt().toString(),
             productOrderVms


     );
    }

    @Override
    @Cacheable(value = "orders" , key = "#accountId + '-' + #page + '-' + #size")
    public UserOrderResponse getOrdersByAccountId(long accountId , int page, int size) {

        AccountDto accountDto = (AccountDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentAccountId = accountDto.getId();
        try {

            Pageable pageable = getPageable(page, size);
            Page<Order> result = orderRepo.findByAccountIdOrderByCodeAsc(currentAccountId, pageable);

            if (result.isEmpty()) {
                throw new RuntimeException("your.history.null");
            }
            List<ResponseOrderVm> orderVms = result.stream().map(order -> {
                List<ProductOrderVm> productOrderVms = order.getOrderProducts().stream().map(
                        orderProduct -> new ProductOrderVm(
                                orderProduct.getProduct().getName(),
                                orderProduct.getProduct().getImagePath(),
                                orderProduct.getProduct().getPrice(),
                                orderProduct.getQuantity(),
                                orderProduct.getSubtotal()
                        )
                ).collect(Collectors.toList());

                return new ResponseOrderVm(
                        order.getCode(),
                        order.getTotalPrice(),
                        order.getTotalNumber(),
                        order.getAccountName(),
                        order.getCreatedAt().toString(),
                        productOrderVms
                );
            }).collect(Collectors.toList());
            Double totalPrice = orderVms.stream().mapToDouble(ResponseOrderVm::getTotalPrice).sum();
            return new UserOrderResponse(orderVms , result.getTotalElements() , totalPrice );
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }
    }

    @Override
    @Cacheable(value = "orders" , key = "#page + '-' + #size")
    public UserOrderResponse getAllOrders(int page, int size) {
       try {
           Pageable pageable = getPageable(page, size);
           Page<Order> result = orderRepo.findAllByOrderByCodeAsc(pageable);
           if (result.isEmpty()) {
               throw new RuntimeException("history.null");
           }
           List<ResponseOrderVm> orderVms = result.stream().map(order -> {
               List<ProductOrderVm> productOrderVms = order.getOrderProducts().stream().map(
                       orderProduct -> new ProductOrderVm(
                               orderProduct.getProduct().getName(),
                               orderProduct.getProduct().getImagePath(),
                               orderProduct.getProduct().getPrice(),
                               orderProduct.getQuantity(),
                               orderProduct.getSubtotal()
                       )
               ).collect(Collectors.toList());

               return new ResponseOrderVm(
                       order.getCode(),
                       order.getTotalPrice(),
                       order.getTotalNumber(),
                       order.getAccountName(),
                       order.getCreatedAt().toString(),
                       productOrderVms
               );
                   }).collect(Collectors.toList());
           Double totalPrice = orderVms.stream().mapToDouble(ResponseOrderVm::getTotalPrice).sum();
           return new UserOrderResponse(orderVms , result.getTotalElements() , totalPrice );
       }catch (Exception e){
           throw new RuntimeException(e.getMessage());
       }
    }

    private Pageable getPageable(int page, int size){
            try {
                if (page < 1) {
                    throw new SystemException("error.min.one.page");
                }
                return PageRequest.of(page - 1, size);
            } catch (SystemException e) {
                throw new RuntimeException(e.getMessage());
            }
        }



}
