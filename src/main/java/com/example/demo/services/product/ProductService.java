package com.example.demo.services.product;

import com.example.demo.dtos.product.ProductDto;
import com.example.demo.dtos.product.ProductRequestDto;
import com.example.demo.entities.product.Product;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.mappers.product.ProductMapper;
import com.example.demo.repositories.product.ProductImageRepository;
import com.example.demo.repositories.product.ProductRepository;
import com.example.demo.repositories.product.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductImageRepository productImageRepository;

    public Page<ProductDto> getAllProducts(String name, String categoryName, Pageable pageable) {
        Specification<Product> specification = productSpecification(name, categoryName);
        return productRepository.findAll(
                        specification,
                        pageable
                )
                .map(productMapper::toDto);
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }

    public Product getReferenceById(UUID id) {
        return productRepository.getReferenceById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Product createProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.createProductFromDto(productRequestDto);
        return productRepository.saveAndFlush(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public Product updateProduct(UUID id, ProductRequestDto productRequestDto) {
        Product product = getReferenceById(id);
        product = productMapper.updateProductFromDto(productRequestDto, product);
        return productRepository.saveAndFlush(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProductById(UUID id) {
        productRepository.findById(id)
                .ifPresentOrElse(
                        productRepository::delete,
                        () -> {
                            throw new NotFoundException("Product not found with id: " + id);
                        });
    }

    public boolean existsProductById(UUID id) {
        return productRepository.existsById(id);
    }

    private Specification<Product> productSpecification(String name, String categoryName) {
        Specification<Product> specification = Specification.where(null);
        if (!Objects.isNull(name)) {
            specification = specification.and(ProductSpecification.hasName(name));
        }
        if (!Objects.isNull(categoryName)) {
            specification = specification.and(ProductSpecification.hasCategoryName(categoryName));
        }
        return specification;
    }
}
