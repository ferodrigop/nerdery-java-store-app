package com.example.demo.dtos.payment;

import com.example.demo.entities.payment.Payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO for {@link Payment}
 */
public record PaymentDto(
        UUID id,
        String stripePaymentIntentId,
        BigDecimal amount,
        String currency,
        String status,
        UUID orderId,
        OffsetDateTime createdAt
) implements Serializable {
}