package com.example.demo.dtos.order;

import com.example.demo.dtos.product.ProductDto;
import com.example.demo.entities.order.OrderItem;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link OrderItem}
 */
public record OrderItemDto(
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice,
        ProductDto product
) implements Serializable {
}