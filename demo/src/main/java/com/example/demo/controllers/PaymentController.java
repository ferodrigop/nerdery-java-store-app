package com.example.demo.controllers;

import com.example.demo.dtos.payment.PaymentIntentRequestDto;
import com.example.demo.dtos.payment.PaymentIntentResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<PaymentIntentResponseDto> createPaymentIntent(@RequestBody PaymentIntentRequestDto paymentIntentRequestDto) {

        return ResponseEntity.ok(PaymentIntentResponseDto.builder().build());
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature
    ) {
        return ResponseEntity.noContent().build();
    }
}
