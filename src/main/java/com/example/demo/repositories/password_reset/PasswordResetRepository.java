package com.example.demo.repositories.password_reset;

import com.example.demo.entities.password_reset.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
    Optional<PasswordReset> findByIdAndExpiresAtAfter(UUID id, Instant date);
}