package com.example.demo.repositories.refresh_token;

import com.example.demo.entities.refresh_token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByIdAndExpiresAtAfter(UUID id, Instant date);
}