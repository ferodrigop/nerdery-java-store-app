package com.example.demo.dtos.cart;

import com.example.demo.dtos.product.ProductDto;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItemDto(
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice,
        ProductDto product
) {
}
