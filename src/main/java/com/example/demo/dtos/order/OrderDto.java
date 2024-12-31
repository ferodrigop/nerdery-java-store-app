package com.example.demo.dtos.order;

import com.example.demo.entities.order.Order;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link Order}
 */
@Builder
public record OrderDto(
        UUID orderId,
        UUID userId,
        BigDecimal total,
        String status,
        List<OrderItemDto> items
) implements Serializable {
}