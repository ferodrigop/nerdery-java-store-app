package com.example.demo.mappers.category;

import com.example.demo.entities.product.Category;
import com.example.demo.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryQualifier {
    private final CategoryService categoryService;

    @Named("idToCategory")
    public Category getCategory(final UUID id) {
        return categoryService.getCategoryById(id);
    }
}
