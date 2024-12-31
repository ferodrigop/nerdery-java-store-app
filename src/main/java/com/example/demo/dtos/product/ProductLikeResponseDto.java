package com.example.demo.dtos.product;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductLikeResponseDto(
        UUID productId,
        Long likes
) {
}
