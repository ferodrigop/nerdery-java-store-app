package com.example.demo.utils;

import com.example.demo.dtos.exception.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExceptionUtils {
    public ErrorResponseDto createErrorResponseDto(
            HttpStatus status,
            String error,
            String message,
            String path
    ) {
        return ErrorResponseDto.builder()
                .status(status.value())
                .error(error)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public ErrorResponseDto createErrorResponseDto(
            HttpStatus status,
            String error,
            String message,
            String path,
            Exception ex
    ) {
        ex.printStackTrace();
        return createErrorResponseDto(status, error, message, path);
    }
}
