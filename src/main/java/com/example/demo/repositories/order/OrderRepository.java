package com.example.demo.repositories.order;

import com.example.demo.entities.order.Order;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Window<Order> findAllByUserId(UUID userId, ScrollPosition position, Limit limit, Sort sort);
}