package com.example.demo.controllers.order;

import com.example.demo.dtos.order.OrderDto;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "25") Integer pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDir
    ) {
        List<OrderDto> orders = new ArrayList<>();
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(OrderDto.builder().build());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "25") Integer pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDir
    ) {
        List<OrderDto> orders = new ArrayList<>();
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderDto> placeOrder() {
        return ResponseEntity.ok(OrderDto.builder().build());
    }
}
