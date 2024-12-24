package com.example.demo.dtos.category;

import com.example.demo.entities.product.Category;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Category}
 */
public record CategoryDto(
        UUID id,
        String name
) implements Serializable {
}