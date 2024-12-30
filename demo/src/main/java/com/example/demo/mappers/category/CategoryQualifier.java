package com.example.demo.mappers.category;

import com.example.demo.entities.product.Category;
import com.example.demo.repositories.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryQualifier {
    private final CategoryRepository categoryRepository;

    @Named("idToCategory")
    public Category getCategory(final UUID id) {
        return categoryRepository.getReferenceById(id);
    }
}
