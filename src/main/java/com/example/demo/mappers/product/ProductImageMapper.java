package com.example.demo.mappers.product;

import com.example.demo.dtos.product.ProductImageDto;
import com.example.demo.entities.product.ProductImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImage toEntity(ProductImageDto productImageDto);

    ProductImageDto toDto(ProductImage productImage);
}