package com.example.demo.controllers.cart;

import com.example.demo.dtos.cart.AddToCartRequestDto;
import com.example.demo.dtos.cart.CartDto;
import com.example.demo.dtos.cart.CartItemDto;
import com.example.demo.dtos.cart.UpdateCartItemQuantityRequestDto;
import com.example.demo.entities.cart.Cart;
import com.example.demo.mappers.cart.CartMapper;
import com.example.demo.services.cart.CartItemService;
import com.example.demo.services.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Controller
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CartMapper cartMapper;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<CartDto> getCart() {
        return ResponseEntity.ok(
                cartMapper.toDto(cartService.getOrCreateCartForUser())
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<CartItemDto> addItemToCart(@RequestBody AddToCartRequestDto addToCartRequestDto) {
        Cart cart = cartService.getOrCreateCartForUser();
        return ResponseEntity.ok(
                cartMapper.toDto(
                        cartItemService.addItemToCart(
                                cart.getId(),
                                addToCartRequestDto.productId(),
                                addToCartRequestDto.quantity()
                        )
                )
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItemQuantity(
            @PathVariable UUID cartItemId,
            @RequestBody UpdateCartItemQuantityRequestDto updateCartItemQuantityRequestDto
    ) {
        return ResponseEntity.ok(
                cartMapper.toDto(
                        cartItemService.updateCartItemQuantityById(
                                cartItemId,
                                updateCartItemQuantityRequestDto.newQuantity()
                        )
                )
        );
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable UUID cartItemId) {
        cartItemService.removeCartItemById(cartItemId);
        return ResponseEntity.notFound().build();
    }
}
