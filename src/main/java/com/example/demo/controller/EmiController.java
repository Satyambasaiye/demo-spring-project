package com.example.demo.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmiOptionsResponse;
import com.example.demo.service.EmiOptionsService;

@RestController
@RequestMapping("/api/v1")
public class EmiController {
    private final EmiOptionsService emiOptionsService;

    public EmiController(EmiOptionsService emiOptionsService) {
        this.emiOptionsService = emiOptionsService;
    }

    @GetMapping("/products/{productId}/emi-options")
    public EmiOptionsResponse getEmiOptions(@PathVariable("productId") UUID  productId) {
        return emiOptionsService.getEmiOptions(productId);
    }
}
