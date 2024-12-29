package com.example.demo.controllers;

import com.example.demo.dtos.auth.*;
import com.example.demo.entities.order.RoleName;
import com.example.demo.services.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UUID newUserId = authenticationService.signUp(signUpRequestDto, RoleName.ROLE_USER);
        return ResponseEntity.ok(
                SignUpResponseDto.builder()
                        .userId(newUserId)
                        .build()
        );
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        SignInResponseDto signInResponseDto = authenticationService.login(signInRequestDto);
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
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        return ResponseEntity.ok("Email sent successfully");
    }

    @PostMapping("/reset-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return ResponseEntity.ok("Password successfully reset");
    }
}
