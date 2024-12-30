package com.example.demo.repositories.password_reset;

import com.example.demo.entities.password_reset.PasswordReset;
import com.example.demo.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, UUID> {
    Optional<PasswordReset> findByIdAndUserAndExpiresAtAfter(UUID id, User user, Instant date);
}