package com.example.demo.dtos.auth;

import lombok.Builder;

@Builder
public record ChangePasswordDto(
        String currentPassword,
        String newPassword,
        String newPasswordConfirmation
) {
}
