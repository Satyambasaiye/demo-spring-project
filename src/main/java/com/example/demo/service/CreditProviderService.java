package com.example.demo.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.repository.CreditProviderRepository;
import com.example.demo.dto.CreditProviderResponse;
import com.example.demo.entity.CreditProviders;

import java.util.List;
import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public  class CreditProviderService {
    private final CreditProviderRepository creditProviderRepo;

    public CreditProviderService(CreditProviderRepository creditProviderRepo){
        this.creditProviderRepo= creditProviderRepo;
    }
    public List<CreditProviderResponse> getEligibleProviders(BigDecimal orderAmount) {
        return creditProviderRepo.findEligibleProviders(orderAmount)
                .stream()
                .map(this::tocreditResponse)
                .toList();
    }

    public List<CreditProviderResponse> getAll() {
        return creditProviderRepo.findAll()
                .stream()
                .map(this::tocreditResponse)
                .toList();
    }
    private CreditProviderResponse tocreditResponse(CreditProviders entity) {
        return new CreditProviderResponse(
                entity.getProviderId(),
                entity.getName(),
                entity.getCode(),
                entity.getProviderType(),
                entity.getLogoUrl(),
                entity.getMinOrderAmount(),
                entity.getMaxOrderAmount(),
                entity.getSupportedTenures(),
                entity.getDefaultInterestRate(),
                entity.getNoCostEmiSupported(),
                entity.getProcessingFee(),
                entity.getProcessingFeeType(),
                entity.getGatewayConfig(),
                entity.getDisplayOrder(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
