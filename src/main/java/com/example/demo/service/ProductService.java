package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.example.demo.repository.ProductRepository;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.ProductResponse;
import com.example.demo.entity.Product;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toProductResponse)
                .toList();
    }
  public ProductResponse findByProductIdAndIsActiveTrue(UUID productId) {
        Product product=productRepository.findByProductIdAndIsActiveTrue(productId)
               .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found with ID: " + productId));
        return toProductResponse(product);
    }
    
    private ProductResponse toProductResponse(Product product) {
       
        return new ProductResponse(
                product.getProductId(),
                product.getSku(),
                product.getName(),
                null, // slug (replace with product.getSlug() if added to Entity)
                product.getDescription(),
                product.getBrand(),
                product.getCategoryId(),
                product.getPrice(),
                product.getCompareAtPrice(),
                product.getCurrency(),
                product.getStockQuantity(),
                product.getStockQuantity() != null && product.getStockQuantity() > 0,
                product.getImageUrl(),
                product.getGalleryUrls() != null ? Arrays.asList(product.getGalleryUrls()) : List.of(),
                product.getAttributes() != null ? product.getAttributes() : Map.of(),
                false, // isFeatured (replace with product.isFeatured() if added to Entity)
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}