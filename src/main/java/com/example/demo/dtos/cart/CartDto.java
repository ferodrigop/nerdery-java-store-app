package com.example.demo.dtos.cart;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record CartDto(
        UUID cartId,
        UUID userId,
        List<CartItemDto> items,
        BigDecimal totalAmount
) {
}
