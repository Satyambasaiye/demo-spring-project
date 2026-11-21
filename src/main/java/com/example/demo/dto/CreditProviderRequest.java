package com.example.demo.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.constants.KromaConstants;

public record CreditProviderRequest(

        // Core Provider Details
        @NotBlank(message = "Provider name is required")
        @Size(max = 150, message = "Provider name must not exceed 150 characters")
        String name,

        @NotBlank(message = "Provider code is required")
        @Size(max = 50, message = "Provider code must not exceed 50 characters")
        @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code must contain only uppercase letters, numbers, and underscores")
        String code,

        @NotNull(message = "Provider type is required")
        String providerType,

        String logoUrl,

        // Transaction & Tenure Rules
        @PositiveOrZero(message = "Minimum order amount cannot be negative")
        @Digits(integer = 8, fraction = 2, message = "Minimum order amount must have up to 8 integer digits and 2 decimal places")
        BigDecimal minOrderAmount,

        @PositiveOrZero(message = "Maximum order amount cannot be negative")
        @Digits(integer = 8, fraction = 2, message = "Maximum order amount must have up to 8 integer digits and 2 decimal places")
        BigDecimal maxOrderAmount,

        @NotEmpty(message = "At least one supported tenure must be provided")
        List<@Positive(message = "Tenure months must be positive") Integer> supportedTenures,

        // Interest & Fee Defaults
        @PositiveOrZero(message = "Default interest rate cannot be negative")
        @Digits(integer = 3, fraction = 2, message = "Interest rate must have up to 3 integer digits and 2 decimal places")
        BigDecimal defaultInterestRate,

        Boolean noCostEmiSupported,

        @PositiveOrZero(message = "Processing fee cannot be negative")
        @Digits(integer = 8, fraction = 2, message = "Processing fee must have up to 8 integer digits and 2 decimal places")
        BigDecimal processingFee,

        String processingFeeType,

        // Gateway Integration & Status
        Map<String, Object> gatewayConfig,

        Integer displayOrder,

        Boolean isActive
) {
    // Cross-field validation mirroring SQL CHECK (max_order_amount IS NULL OR max_order_amount >= min_order_amount)
    @AssertTrue(message = "Maximum order amount must be greater than or equal to minimum order amount")
    public boolean isValidOrderAmountRange() {
        if (minOrderAmount == null || maxOrderAmount == null) {
            return true;
        }
        return maxOrderAmount.compareTo(minOrderAmount) >= 0;
    }

    // Apply SQL column defaults when optional fields are omitted in JSON
    public CreditProviderRequest {
        if (minOrderAmount == null) minOrderAmount = new BigDecimal("3000.00");
        if (supportedTenures == null) supportedTenures = List.of(3, 6, 9, 12);
        if (defaultInterestRate == null) defaultInterestRate = new BigDecimal("15.00");
        if (noCostEmiSupported == null) noCostEmiSupported = false;
        if (processingFee == null) processingFee = BigDecimal.ZERO;
        if (processingFeeType == null) processingFeeType = KromaConstants.ProcessingFeeTypeF;
        if (gatewayConfig == null) gatewayConfig = new HashMap<>();
        if (displayOrder == null) displayOrder = 0;
        if (isActive == null) isActive = true;
    }
}
