package com.example.demo.dtos.auth;

import lombok.Builder;

@Builder
public record SignInResponseDto(
        String accessToken,
        String refreshToken
) {
}
