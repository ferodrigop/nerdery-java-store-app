package com.example.demo.services.refresh_token;

import com.example.demo.entities.refresh_token.RefreshToken;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.refresh_token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.jwt.refresh-token-expiration-time}")
    private long refreshTokenExpirationMs;

    @Transactional
    public RefreshToken createNewRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(Instant.now().plusMillis(refreshTokenExpirationMs));
        return refreshTokenRepository.saveAndFlush(refreshToken);
    }

    public RefreshToken findRefreshTokenByIdAndExpiresAtAfter(UUID refreshTokenId, Instant date) {
        return refreshTokenRepository.findByIdAndExpiresAtAfter(refreshTokenId, date)
                .orElseThrow(() -> new NotFoundException("Invalid or expired refresh token id"));
    }

    @Transactional
    public void deleteRefreshTokenById(UUID id) {
        refreshTokenRepository.deleteById(id);
    }
}
