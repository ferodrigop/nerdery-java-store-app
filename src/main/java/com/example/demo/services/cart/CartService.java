package com.example.demo.services.cart;

import com.example.demo.entities.cart.Cart;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.cart.CartRepository;
import com.example.demo.utils.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final IAuthenticationFacade authenticationFacade;

    public Cart getOrCreateCartForUser() {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        return cartRepository.findByUserId(user.getId())
                .orElse(
                        cartRepository.saveAndFlush(
                                Cart.builder()
                                        .user(user)
                                        .items(new ArrayList<>())
                                        .build()
                        )
                );
    }

    public Cart getCartById(UUID id) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + id));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("Cart not found with id: " + id);
        }

        return cart;
    }

    public Cart getReferenceById(UUID id) {
        return cartRepository.getReferenceById(id);
    }
}
