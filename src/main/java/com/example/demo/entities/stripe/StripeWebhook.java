package com.example.demo.entities.stripe;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "stripe_webhook")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StripeWebhook {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, unique = true)
    private UUID id;

    @Column(nullable = false)
    private String eventId;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private String payload;

    @Column(nullable = false)
    private Instant receivedAt;
}
