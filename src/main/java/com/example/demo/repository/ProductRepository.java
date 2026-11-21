package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    // Fetch a single active product by its SEO slug
    Optional<Product> findBySlugAndIsActiveTrue(String slug);

    // Fetch a single active product by its UUID
    Optional<Product> findByProductIdAndIsActiveTrue(UUID productId);

    // Check if an SKU already exists before creating a new product
    boolean existsBySku(String sku);
    // Fetch a single active product by its UUID
    List<Product> findByCategoryId(Integer categoryId);
}
