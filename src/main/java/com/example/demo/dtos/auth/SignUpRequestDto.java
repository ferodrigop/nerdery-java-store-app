package com.example.demo.dtos.auth;

public record SignUpRequestDto(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
