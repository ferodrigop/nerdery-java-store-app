package com.example.demo.repositories.stripe;

import com.example.demo.entities.stripe.StripeWebhook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StripeWebhookRepository extends JpaRepository<StripeWebhook, UUID> {
}