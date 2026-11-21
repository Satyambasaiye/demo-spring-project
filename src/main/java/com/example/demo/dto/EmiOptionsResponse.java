package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record EmiOptionsResponse(
        UUID productId,
        String productName,
        String currency,
        BigDecimal productPrice,
        List<ProviderOptions> providers) {

    public record ProviderOptions(
            UUID providerId,
            String providerName,
            String code,
            Boolean noCostEmiSupported,
            List<EmiTerm> offers) {
    }

    public record EmiTerm(
            int tenureMonths,
            BigDecimal annualInterestRate,
            BigDecimal monthlyEmi,
            BigDecimal totalInterest,
            BigDecimal totalPayable,
            BigDecimal processingFee) {
    }
}
