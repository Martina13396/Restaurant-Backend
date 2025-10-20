package com.example.restaurant.mapper;

import com.example.restaurant.model.Order;
import com.example.restaurant.service.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderDto orderDto(Order order);

    OrderDto orderDto(OrderDto orderDto);

    List<OrderDto> orderDtos(List<Order> orders);

    List<Order> orders(List<OrderDto> orderDtos);
}
