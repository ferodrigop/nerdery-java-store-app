package com.example.demo.dtos.auth;

import java.util.UUID;

public record SignOutDto(
        UUID refreshToken
) {
}
