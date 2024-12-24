package com.example.demo.mappers.product;

import com.example.demo.dtos.product.ProductDto;
import com.example.demo.dtos.product.ProductRequestDto;
import com.example.demo.entities.product.Product;
import com.example.demo.mappers.category.CategoryQualifier;
import org.mapstruct.*;

import java.time.Instant;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ProductImageMapper.class, CategoryQualifier.class},
        imports = Instant.class
)
public interface ProductMapper {
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "likes", ignore = true)
    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);

    List<ProductDto> toDtoList(List<Product> products);

    List<Product> toEntityList(List<ProductDto> productDtos);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "idToCategory")
    @Mapping(target = "createdAt", expression = "java( Instant.now() )")
    Product createProductFromDto(ProductRequestDto productRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "isEnabled", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product updateProductFromDto(ProductRequestDto productRequestDto, @MappingTarget Product product);
}