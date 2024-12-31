package com.example.demo.repositories.product;

import com.example.demo.entities.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
    List<ProductImage> findAllByProductId(UUID productId);

    Long countAllByProductId(UUID productId);

    @Query("""
            SELECT p
            FROM ProductImage p
            WHERE p.product.id=:productId
            AND p.isMain = true
            """)
    Optional<ProductImage> findOneByProductIdAndIsMain(UUID productId);

    @Modifying
    @Query("""
                UPDATE ProductImage pi SET pi.isMain = false
                WHERE pi.product.id = :productId AND pi.isMain = true
            """)
    void updateIsMainToFalseByProductId(@Param("productId") UUID productId);


    @Query("""
            SELECT p
            FROM ProductImage p
            WHERE p.product.id=:productId
            AND p.id <> :excludeImageId
            AND p.isMain = false
            ORDER BY p.createdAt ASC
            LIMIT 1
            """)
    Optional<ProductImage> findFirstByProductIdAndIdNotOrderByCreatedAtAsc(@Param("productId") UUID productId, @Param("excludeImageId") UUID excludeImageId);
}