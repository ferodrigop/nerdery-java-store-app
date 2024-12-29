package com.example.demo.dtos.auth;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SignUpResponseDto(
        UUID userId
) {
}
