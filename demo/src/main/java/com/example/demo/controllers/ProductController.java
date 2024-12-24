package com.example.demo.controllers;

import com.example.demo.dtos.product.ProductDto;
import com.example.demo.dtos.product.ProductImageDto;
import com.example.demo.dtos.product.ProductLikeResponseDto;
import com.example.demo.dtos.product.ProductRequestDto;
import com.example.demo.mappers.product.ProductImageMapper;
import com.example.demo.mappers.product.ProductMapper;
import com.example.demo.services.product.ProductImageService;
import com.example.demo.services.product.ProductLikeService;
import com.example.demo.services.product.ProductService;
import com.example.demo.utils.PageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final ProductLikeService productLikeService;
    private final ProductImageService productImageService;
    private final ProductImageMapper productImageMapper;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "25") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortDir
    ) {
        Pageable pageable = PageBuilder.buildPageRequest(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(
                productService.getAllProducts(pageable)
                        .map(productMapper::toDto)
        );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(
            @PathVariable UUID productId
    ) {
        return ResponseEntity.ok(
                productMapper.toDto(
                        productService.getProductById(productId)
                )
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(
                productMapper.toDto(
                        productService.createProduct(productRequestDto)
                )
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable UUID productId,
            @RequestBody ProductRequestDto productRequestDto
    ) {
        return ResponseEntity.ok(
                productMapper.toDto(
                        productService.updateProduct(productId, productRequestDto)
                )
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}/likes")
    public ResponseEntity<ProductLikeResponseDto> getProductLikesById(@PathVariable UUID productId) {
        return ResponseEntity.ok(
                productLikeService.getTotalProductLike(productId)
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{productId}/likes")
    public ResponseEntity<?> likeProductById(@PathVariable UUID productId) {
        productLikeService.likeProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}/images")
    public ResponseEntity<List<ProductImageDto>> getProductImages(@PathVariable UUID productId) {
        return ResponseEntity.ok(
                productImageService.getAllProductImagesByProductId(productId)
                        .stream()
                        .map(productImageMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/{productId}/images/{imageId}")
    public ResponseEntity<ProductImageDto> getProductImageById(
            @PathVariable UUID productId,
            @PathVariable UUID imageId
    ) {

        return ResponseEntity.ok(
                productImageMapper.toDto(
                        productImageService.getProductImageById(imageId)
                )
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/{productId}/images")
    public ResponseEntity<ProductImageDto> addProductImage(
            @PathVariable UUID productId,
            @RequestPart("image") MultipartFile file
    ) {
        return ResponseEntity.ok(
                productImageMapper.toDto(
                        productImageService.addProductImage(productId, file)
                )
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{productId}/images/{imageId}")
    public ResponseEntity<ProductImageDto> updateProductImage(
            @PathVariable UUID productId,
            @PathVariable UUID imageId,
            @RequestPart("image") MultipartFile file
    ) {
        return ResponseEntity.ok(
                productImageMapper.toDto(
                        productImageService.replaceProductImageById(imageId, file)
                )
        );
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<ProductImageDto> deleteProductImage(
            @PathVariable UUID productId,
            @PathVariable UUID imageId
    ) {
        productImageService.deleteProductImageById(imageId);
        return ResponseEntity.noContent().build();
    }
}
