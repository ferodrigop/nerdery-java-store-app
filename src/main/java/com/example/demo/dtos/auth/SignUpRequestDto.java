package com.example.demo.dtos.auth;

import lombok.Builder;

@Builder
public record SignUpRequestDto(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
