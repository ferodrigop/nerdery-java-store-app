package com.example.demo.controllers.order;

import com.example.demo.dtos.product.ProductDto;
import com.example.demo.entities.order.Order;
import com.example.demo.entities.order.OrderItem;
import com.example.demo.entities.user.User;
import com.example.demo.mappers.order.OrderMapper;
import com.example.demo.mappers.product.ProductMapper;
import com.example.demo.repositories.order.OrderItemRepository;
import com.example.demo.repositories.order.OrderRepository;
import com.example.demo.services.order.OrderService;
import com.example.demo.services.product.ProductService;
import com.example.demo.utils.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.query.ScrollSubrange;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class OrderGraphQLController {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;
    private final OrderService orderService;
    private final ProductService productService;
    private final IAuthenticationFacade authenticationFacade;

    //@Secured("ROLE_USER")
    @QueryMapping
    public Order order(@Argument final UUID id) {
        return orderService.findOrderById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public Window<Order> userOrders(
            @Argument final UUID userId,
            ScrollSubrange subrange
    ) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        System.out.println(user.getId());
        ScrollPosition scrollPosition = subrange.position().orElse(ScrollPosition.offset());
        Limit limit = Limit.of(subrange.count().orElse(10));
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return orderRepository.findAllByUserId(userId, scrollPosition, limit, sort);
    }

    @SchemaMapping
    public User user(Order order) {
        return order.getUser();
    }

    @SchemaMapping
    public List<OrderItem> items(
            Order order) {
        return orderItemRepository.findAllByOrderId(order.getId());
    }

    @SchemaMapping
    public ProductDto product(OrderItem orderItem) {
        return productMapper.toDto(productService.getProductById(orderItem.getProduct().getId()));
    }
}
