package com.example.demo.repositories.product;

import com.example.demo.entities.product.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Product findAndLockProductById(@Param("productId") UUID productId);

}