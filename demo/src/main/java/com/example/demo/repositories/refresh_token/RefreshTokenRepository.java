package com.example.demo.repositories.refresh_token;

import com.example.demo.entities.refresh_token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
}