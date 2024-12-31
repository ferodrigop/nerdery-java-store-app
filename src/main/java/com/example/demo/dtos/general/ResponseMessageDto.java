package com.example.demo.dtos.general;

import lombok.Builder;

@Builder
public record ResponseMessageDto(
        String message
) {
}
