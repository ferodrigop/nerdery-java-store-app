package com.example.demo.services.authentication;

import com.example.demo.entities.password_reset.PasswordReset;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.password_reset.PasswordResetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PasswordResetService {
    private final PasswordResetRepository passwordResetRepository;

    @Value("${api.password.reset.ttl}")
    private long secretKey;

    @Transactional
    public PasswordReset createPasswordReset(User user) {
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUser(user);
        passwordReset.setExpiresAt(Instant.now().plusSeconds(secretKey));
        return passwordResetRepository.saveAndFlush(passwordReset);
    }

    public PasswordReset findPasswordResetByIdAndUserAndExpiresAtAfter(UUID id, User user, Instant date) {
        return passwordResetRepository.findByIdAndUserAndExpiresAtAfter(id, user, date)
                .orElseThrow(() -> new NotFoundException("Invalid or expired reset token"));
    }

    @Transactional
    public void deletePasswordResetById(UUID id) {
        passwordResetRepository.deleteById(id);
    }
}
