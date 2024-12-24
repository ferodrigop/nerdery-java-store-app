package com.example.demo.dtos.product;

import com.example.demo.dtos.category.CategoryDto;
import com.example.demo.entities.product.Product;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link Product}
 */
@Builder
public record ProductDto(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        BigInteger stock,
        Boolean isEnabled,
        CategoryDto category,
        List<ProductImageDto> images
) implements Serializable {
}