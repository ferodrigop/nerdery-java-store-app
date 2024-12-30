package com.example.demo.exceptions;

import com.example.demo.dtos.exception.ErrorResponseDto;
import com.example.demo.utils.ExceptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ExceptionUtils exceptionUtils;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDto errorResponseDto = exceptionUtils.createErrorResponseDto(
                status,
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = exceptionUtils.createErrorResponseDto(
                status,
                "Invalid credentials",
                "The provided email or password is incorrect",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUsernameNotFound(UsernameNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = exceptionUtils.createErrorResponseDto(
                status,
                "Invalid credentials",
                "The provided email or password is incorrect",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponseDto> handleDisabled(DisabledException ex, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = exceptionUtils.createErrorResponseDto(
                status,
                "Account disabled",
                "Your account has been disabled. Please contact support",
                request.getDescription(false).replace("uri=", ""),
                ex
        );
        return new ResponseEntity<>(errorResponseDto, status);
    }
}
