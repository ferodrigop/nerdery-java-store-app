package com.example.demo.controllers.payment;

import com.example.demo.dtos.payment.PaymentIntentRequestDto;
import com.example.demo.dtos.payment.PaymentIntentResponseDto;
import com.example.demo.services.stripe.StripeService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final StripeService stripeService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<PaymentIntentResponseDto> createPaymentIntent(@RequestBody PaymentIntentRequestDto paymentIntentRequestDto) throws StripeException {
        return ResponseEntity.ok(stripeService.createPaymentIntent(paymentIntentRequestDto));
    }

    @PostMapping("/stripe/webhook")
    public ResponseEntity<?> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature
    ) {
        try {
            stripeService.handleWebhookEvent(payload, signature);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
