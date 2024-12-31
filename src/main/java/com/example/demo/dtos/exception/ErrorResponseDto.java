package com.example.demo.dtos.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponseDto(
        int status,             // HTTP status code
        String error,           // Short error description
        String message,         // Detailed error message for context
        String path,            // The request path that caused the exception
        LocalDateTime timestamp // Timestamp when the error occurred
) {
}
