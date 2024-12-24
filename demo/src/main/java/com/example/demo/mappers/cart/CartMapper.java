package com.example.demo.mappers.cart;

import com.example.demo.dtos.cart.CartDto;
import com.example.demo.dtos.cart.CartItemDto;
import com.example.demo.entities.cart.Cart;
import com.example.demo.entities.cart.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toEntity(CartDto cartDto);

    CartDto toDto(Cart cart);

    CartItemDto toDto(CartItem cartItem);

    CartItem toEntity(CartItemDto cartItemDto);
}
