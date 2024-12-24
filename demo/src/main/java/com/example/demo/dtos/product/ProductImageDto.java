package com.example.demo.dtos.product;

import com.example.demo.entities.product.ProductImage;
import lombok.Builder;

/**
 * DTO for {@link ProductImage}
 */
@Builder
public record ProductImageDto(
        String imageUrl,
        String fileType,
        Boolean isMain
) {
}
