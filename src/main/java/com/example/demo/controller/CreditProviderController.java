package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CreditProviderResponse;
import com.example.demo.service.CreditProviderService;

@RestController
@RequestMapping("/api/v1/creditprovider")
public class CreditProviderController {
private final CreditProviderService creditservice;

    public CreditProviderController(CreditProviderService creditservice) {
        this.creditservice = creditservice;
    }
    @GetMapping
    public ResponseEntity<List<CreditProviderResponse>> findEligibleCreditProviders(@RequestParam ("amount") BigDecimal orderAmount) {
        return ResponseEntity.ok(creditservice.getEligibleProviders(orderAmount));
    } 
}
