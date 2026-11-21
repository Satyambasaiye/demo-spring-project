package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductResponse;
import com.example.demo.service.ProductService;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    } 

    @GetMapping
    public ResponseEntity<ProductResponse> getProductsById(@RequestParam ("productId") UUID productId) {
        return ResponseEntity.ok(productService.findByProductIdAndIsActiveTrue(productId));
    } 
}
