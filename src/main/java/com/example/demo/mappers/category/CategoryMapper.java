package com.example.demo.mappers.category;

import com.example.demo.dtos.category.CategoryDto;
import com.example.demo.entities.product.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Category partialUpdate(CategoryDto categoryDto, @MappingTarget Category category);
}