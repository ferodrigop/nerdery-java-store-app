package com.example.demo.controllers.category;

import com.example.demo.dtos.category.CategoryDto;
import com.example.demo.dtos.category.CategoryRequestDto;
import com.example.demo.mappers.category.CategoryMapper;
import com.example.demo.services.category.CategoryService;
import com.example.demo.utils.PageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> getAllCategories(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "25") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDir
    ) {
        Pageable pageable = PageBuilder.buildPageRequest(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(
                categoryService.getAllCategories(pageable)
                        .map(categoryMapper::toDto)
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(
            @PathVariable UUID categoryId
    ) {
        return ResponseEntity.ok(
                categoryMapper.toDto(
                        categoryService.getCategoryById(categoryId)
                )
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return ResponseEntity.ok(
                categoryMapper.toDto(
                        categoryService.createCategory(categoryRequestDto)
                )
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable UUID categoryId,
            @RequestBody CategoryRequestDto categoryRequestDto
    ) {
        return ResponseEntity.ok(
                categoryMapper.toDto(
                        categoryService.updateCategory(categoryId, categoryRequestDto)
                )
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.noContent().build();
    }
}
