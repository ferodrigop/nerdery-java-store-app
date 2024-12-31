package com.example.demo.dtos.auth;

import java.util.UUID;

public record RefreshTokenRequestDto(
        UUID refreshToken
) {
}
