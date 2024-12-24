package com.example.demo.dtos.cart;

import java.math.BigDecimal;
import java.util.UUID;

public record AddToCartRequestDto(
        UUID productId,
        Integer quantity,
        BigDecimal unitPrice
) {
}
