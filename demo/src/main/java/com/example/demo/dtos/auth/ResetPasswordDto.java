package com.example.demo.dtos.auth;

public record ResetPasswordDto(
        String currentPassword,
        String newPassword,
        String newPasswordConfirmation
) {
}
