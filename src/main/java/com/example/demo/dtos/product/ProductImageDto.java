package com.example.demo.dtos.product;

import com.example.demo.entities.product.ProductImage;
import lombok.Builder;

import java.util.UUID;

/**
 * DTO for {@link ProductImage}
 */
@Builder
public record ProductImageDto(
        UUID id,
        String imageUrl,
        String fileType,
        Boolean isMain
) {
}
