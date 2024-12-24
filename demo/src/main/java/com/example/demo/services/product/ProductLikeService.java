package com.example.demo.services.product;

import com.example.demo.dtos.product.ProductLikeResponseDto;
import com.example.demo.entities.product.Product;
import com.example.demo.entities.product.ProductLike;
import com.example.demo.entities.user.User;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.product.ProductLikeRepository;
import com.example.demo.utils.IAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductLikeService {
    private final ProductLikeRepository productLikeRepository;
    private final ProductService productService;
    private final IAuthenticationFacade authenticationFacade;

    @Transactional(rollbackFor = Exception.class)
    public void likeProduct(UUID productId) {
        User user = (User) authenticationFacade.getAuthentication().getPrincipal();
        Product product = productService.getReferenceById(productId);
        productLikeRepository.findByProductIdAndUserId(productId, user.getId())
                .ifPresentOrElse(
                        productLikeRepository::delete,
                        () -> {
                            productLikeRepository.saveAndFlush(
                                    ProductLike.builder()
                                            .user(user)
                                            .product(product)
                                            .build()
                            );
                        });
    }

    @Transactional
    public ProductLikeResponseDto getTotalProductLike(UUID productId) {
        if (!productService.existsProductById(productId)) {
            throw new NotFoundException("Product not found with id: " + productId);
        }
        Long count = productLikeRepository.countByProductId(productId);
        return ProductLikeResponseDto.builder()
                .productId(productId)
                .likes(count)
                .build();
    }
}
