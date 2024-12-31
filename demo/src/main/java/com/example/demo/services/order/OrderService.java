package com.example.demo.services.order;

import com.example.demo.entities.order.Order;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.order.OrderRepository;
import com.example.demo.utils.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final IAuthenticationFacade authenticationFacade;

    public Order findOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id " + id));
    }
}
