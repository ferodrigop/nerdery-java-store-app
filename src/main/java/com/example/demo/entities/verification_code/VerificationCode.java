package com.example.demo.entities.verification_code;

import com.example.demo.entities.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "verification_code")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, unique = true)
    private UUID id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;
}
