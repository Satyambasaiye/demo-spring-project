package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductResponse(
    UUID productId,
    String sku,
    String name,
    String slug,
    String description,
    String brand,
    Integer categoryId,
    
    // Pricing & Availability
    BigDecimal price,
    BigDecimal compareAtPrice,
    String currency,
    Integer stockQuantity,
    boolean inStock,
    
    // Media & Dynamic JSONB Specs
    String imageUrl,
    List<String> galleryUrls,
    Map<String, Object> attributes,
    
    // Status & Metadata
    boolean isFeatured,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {}
