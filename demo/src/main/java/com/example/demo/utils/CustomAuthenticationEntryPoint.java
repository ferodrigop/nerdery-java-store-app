package com.example.demo.utils;

import com.example.demo.dtos.exception.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        // Default error message
        String error = "Invalid credentials";
        String description = "The provided email or password is incorrect";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorResponseDto errorResponseDto = ExceptionUtils.createErrorResponseDto(
                status,
                error,
                description,
                request.getRequestURI(),
                e
        );
        String json = objectMapper.writeValueAsString(errorResponseDto);
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(json);
    }
}
