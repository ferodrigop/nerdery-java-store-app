package com.example.demo.repositories.product;

import com.example.demo.entities.product.ProductLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductLikeRepository extends JpaRepository<ProductLike, UUID> {
    Long countByProductId(UUID productId);

    Optional<ProductLike> findByProductIdAndUserId(UUID productId, UUID userId);
}