package com.example.demo.repositories.stripe;

import com.example.demo.entities.stripe.StripeWebhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StripeWebhookRepository extends JpaRepository<StripeWebhook, UUID> {
}