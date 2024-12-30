package com.example.demo.services.product;

import com.example.demo.entities.product.ProductImage;
import com.example.demo.exceptions.CannotDeleteLastImageException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.product.ProductImageRepository;
import com.example.demo.services.aws.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductService productService;
    private final S3Service s3Service;

    public List<ProductImage> getAllProductImagesByProductId(UUID productId) {
        if (!productService.existsProductById(productId)) {
            throw new NotFoundException("Product not found with id: " + productId);
        }
        return productImageRepository.findAllByProductId(productId);
    }

    public ProductImage getProductImageById(UUID productImageId) {
        return productImageRepository.findById(productImageId)
                .orElseThrow(() -> new NotFoundException("Product image not found with id: " + productImageId));
    }

    public String uploadImage(MultipartFile file) {
        String fileKey = UUID.randomUUID() + "-" + file.getOriginalFilename();
        return s3Service.uploadFile(file, fileKey);
    }

    @Transactional
    public ProductImage addProductImage(UUID productId, MultipartFile file) {
        String url = uploadImage(file);
        ProductImage productImage = ProductImage.builder()
                .imageUrl(url)
                .isMain(false)
                .product(productService.getReferenceById(productId))
                .build();
        return productImageRepository.saveAndFlush(productImage);
    }

    @Transactional
    public ProductImage replaceProductImageById(UUID productImageId, MultipartFile file) {
        ProductImage productImage = getProductImageById(productImageId);
        s3Service.deleteObject(s3Service.extractKeyFromUrl(productImage.getImageUrl()));
        String url = uploadImage(file);
        productImage.setImageUrl(url);
        return productImageRepository.saveAndFlush(productImage);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductImage changeMainImageByProductImageId(UUID productImageId) {
        ProductImage productImage = productImageRepository.findById(productImageId)
                .orElseThrow(() -> new NotFoundException("Product image not found with id: " + productImageId));
        if (productImage.getIsMain()) {
            return productImage;
        }
        productImageRepository.updateIsMainToFalseByProductId(productImage.getProduct().getId());
        productImage.setIsMain(true);
        return productImageRepository.saveAndFlush(productImage);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteProductImageById(UUID productImageId) {
        ProductImage productImage = getProductImageById(productImageId);
        s3Service.deleteObject(s3Service.extractKeyFromUrl(productImage.getImageUrl()));
        if (productImage.getIsMain()) {
            productImage.setIsMain(false);
            productImageRepository.findFirstByProductIdAndIdNotOrderByCreatedAtAsc(productImage.getProduct().getId(), productImageId)
                    .ifPresentOrElse(nextImage -> {
                                nextImage.setIsMain(true);
                                productImageRepository.saveAndFlush(nextImage);
                            },
                            () -> {
                                throw new CannotDeleteLastImageException("Cannot delete the main image as it is the last one for product with id: " + productImage.getProduct().getId());
                            });
        }
        productImageRepository.deleteById(productImageId);
    }
}
