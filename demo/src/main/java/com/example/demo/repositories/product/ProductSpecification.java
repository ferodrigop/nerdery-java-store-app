package com.example.demo.repositories.product;

import com.example.demo.entities.product.Category;
import com.example.demo.entities.product.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> hasCategoryName(String categoryName) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Join<Product, Category> categoryJoin = root.join("category");
            return cb.like(categoryJoin.get("name"), "%" + categoryName + "%");
        };
    }
}
