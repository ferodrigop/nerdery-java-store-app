package com.example.demo.dtos.auth;

import lombok.Builder;

@Builder
public record SignInRequestDto(
        String email,
        String password
) {
}
