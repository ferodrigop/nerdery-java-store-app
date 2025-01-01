package com.example.demo.dtos.order;

import com.example.demo.dtos.product.ProductDto;
import com.example.demo.entities.order.OrderItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link OrderItem}
 */
public record OrderItemDto(
        UUID id,
        Integer quantity,
        BigDecimal unitPrice,
        ProductDto product
) implements Serializable {
}