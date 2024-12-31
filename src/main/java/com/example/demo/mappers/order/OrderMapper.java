package com.example.demo.mappers.order;

import com.example.demo.dtos.order.OrderDto;
import com.example.demo.dtos.order.OrderItemDto;
import com.example.demo.entities.order.Order;
import com.example.demo.entities.order.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);

    Order toEntity(OrderDto orderDto);

    OrderItemDto toDto(OrderItem orderItem);

    OrderItem toEntity(OrderItemDto orderItemDTO);
}

