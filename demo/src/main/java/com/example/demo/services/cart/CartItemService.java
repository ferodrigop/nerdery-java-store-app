package com.example.demo.services.cart;

import com.example.demo.entities.cart.CartItem;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.cart.CartItemRepository;
import com.example.demo.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;

    @Transactional(rollbackFor = Exception.class)
    public CartItem addItemToCart(UUID cartId, UUID productId, int quantity) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .orElse(
                        cartItemRepository.saveAndFlush(
                                CartItem.builder()
                                        .quantity(quantity)
                                        .cart(cartService.getReferenceById(cartId))
                                        .product(productService.getReferenceById(productId))
                                        .build()
                        )
                );
    }

    @Transactional(rollbackFor = Exception.class)
    public CartItem updateCartItemQuantityById(UUID id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart Item not found with id " + id));
        cartItem.setQuantity(quantity);
        return cartItemRepository.saveAndFlush(cartItem);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeCartItemById(UUID id) {
        cartItemRepository.delete(cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart Item not found with id " + id)));
    }
}
