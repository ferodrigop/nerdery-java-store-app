package com.example.demo.dtos.auth;

public record ChangePasswordDto(
        String currentPassword,
        String newPassword,
        String newPasswordConfirmation
) {
}
