package com.example.demo.dtos.payment;

import lombok.Builder;

@Builder
public record PaymentIntentResponseDto(
        String clientSecret
) {
}
