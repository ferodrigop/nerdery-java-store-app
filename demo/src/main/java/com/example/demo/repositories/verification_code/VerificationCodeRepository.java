package com.example.demo.repositories.verification_code;

import com.example.demo.entities.verification_code.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {
}