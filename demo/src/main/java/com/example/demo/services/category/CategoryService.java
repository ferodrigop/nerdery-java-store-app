package com.example.demo.services.category;

import com.example.demo.dtos.category.CategoryRequestDto;
import com.example.demo.entities.product.Category;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }

    @Transactional
    public Category createCategory(CategoryRequestDto categoryRequestDto) {
        Category newCategory = new Category();
        newCategory.setName(categoryRequestDto.name());
        return categoryRepository.saveAndFlush(newCategory);
    }

    @Transactional
    public Category updateCategory(UUID id, CategoryRequestDto categoryRequestDto) {
        Category category = getCategoryById(id);
        category.setName(categoryRequestDto.name());
        return categoryRepository.saveAndFlush(category);
    }

    @Transactional
    public void deleteCategoryById(UUID id) {
        categoryRepository.deleteById(id);
    }
}
