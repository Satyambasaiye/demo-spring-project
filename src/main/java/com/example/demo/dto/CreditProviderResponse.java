package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.demo.entity.CreditProviders;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public record CreditProviderResponse(

        // Primary Identifier

        UUID providerId,

        // Core Provider Details
        String name,
        String code,
        String providerType,
        String logoUrl,

        // Transaction & Tenure Rules
        BigDecimal minOrderAmount,
        BigDecimal maxOrderAmount,
        List<Integer> supportedTenures,

        // Interest & Fee Defaults
        BigDecimal defaultInterestRate,
        Boolean noCostEmiSupported,
        BigDecimal processingFee,
        String processingFeeType,

        // Gateway Integration & Status
        Map<String, Object> gatewayConfig,
        Integer displayOrder,
        Boolean isActive,

        // Audit Timestamps
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
        
) {

    // Convenience mapper from JPA Entity to Response DTO
    public static CreditProviderResponse fromEntity(CreditProviders entity) {
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
                entity.getUpdatedAt()
        );
    }
}
