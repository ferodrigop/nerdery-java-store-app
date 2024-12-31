package com.example.demo.dtos.auth;

import java.util.UUID;

public record ResetPasswordDto(
        UUID token,
        String newPassword,
        String newPasswordConfirmation
) {
}
