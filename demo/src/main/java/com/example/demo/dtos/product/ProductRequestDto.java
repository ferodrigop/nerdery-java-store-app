package com.example.demo.dtos.product;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

public record ProductRequestDto(
        String name,
        String description,
        BigDecimal price,
        BigInteger stock,
        Boolean isEnabled,
        UUID categoryId
) {
}
