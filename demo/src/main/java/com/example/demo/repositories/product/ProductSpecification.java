package com.example.demo.repositories.product;

import com.example.demo.entities.product.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.like(root.get("name"), "%" + name + "%");
    }
}
