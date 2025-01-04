package com.example.demo.controllers.auth;

import com.example.demo.dtos.auth.*;
import com.example.demo.dtos.general.ResponseMessageDto;
import com.example.demo.entities.order.RoleName;
import com.example.demo.services.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    // todo: add email confirmation requirement to activate account
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UUID newUserId = authenticationService.signUp(signUpRequestDto, RoleName.ROLE_USER);
        URI location = URI.create(String.format("/api/v1/users/%s", newUserId));
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        SignInResponseDto signInResponseDto = authenticationService.signIn(signInRequestDto);
        return ResponseEntity.ok(signInResponseDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<SignInResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        SignInResponseDto signInResponseDto = authenticationService.refreshToken(
                refreshTokenRequestDto.refreshToken()
        );
        return ResponseEntity.ok(signInResponseDto);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<Void> signOut(@RequestBody SignOutDto signOutDto) {
        authenticationService.signOut(signOutDto.refreshToken());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseMessageDto> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        authenticationService.initiatePasswordReset(forgotPasswordDto);
        return ResponseEntity.ok(
                ResponseMessageDto.builder()
                        .message("If the email is associated with an account, a reset link has been sent")
                        .build()
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseMessageDto> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        authenticationService.resetPassword(resetPasswordDto);
        return ResponseEntity.ok(
                ResponseMessageDto.builder()
                        .message("Password reset successfully. You may now log in")
                        .build()
        );
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseMessageDto> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        authenticationService.changePassword(changePasswordDto);
        return ResponseEntity.ok(
                ResponseMessageDto.builder()
                        .message("Password successfully changed")
                        .build()
        );
    }
}
