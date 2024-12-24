package com.example.demo.controllers;

import com.example.demo.dtos.auth.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return ResponseEntity.ok("Sign up successful, you may proceed to sign in");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok(SignInResponseDto.builder().build());
    }

    @PostMapping("/sign-out")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> signOut(@RequestBody SignOutDto signOutDto) {
        return ResponseEntity.ok("Sign out successful");
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
