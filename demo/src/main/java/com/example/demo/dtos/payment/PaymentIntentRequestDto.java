package com.example.demo.dtos.payment;

import java.util.UUID;

public record PaymentIntentRequestDto(
        UUID orderId
) {
}
