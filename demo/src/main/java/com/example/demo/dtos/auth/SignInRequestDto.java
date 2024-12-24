package com.example.demo.dtos.auth;

public record SignInRequestDto(
        String email,
        String password
) {
}
