package com.example.demo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.client.CreditProviderApiClient;
import com.example.demo.client.ProductApiClient;
import com.example.demo.dto.CreditProviderResponse;
import com.example.demo.dto.EmiOptionsResponse;
import com.example.demo.dto.EmiOptionsResponse.EmiTerm;
import com.example.demo.dto.EmiOptionsResponse.ProviderOptions;
import com.example.demo.dto.ProductResponse;

@Service
public class EmiOptionsService {
    private static final List<Integer> REQUESTED_TENURES = List.of(3, 6, 9);

    private final ProductApiClient productApiClient;
    private final CreditProviderApiClient creditProviderApiClient;
    private final EmiCalculator emiCalculator;

    public EmiOptionsService(
            ProductApiClient productApiClient,
            CreditProviderApiClient creditProviderApiClient,
            EmiCalculator emiCalculator) {
        this.productApiClient = productApiClient;
        this.creditProviderApiClient = creditProviderApiClient;
        this.emiCalculator = emiCalculator;
    }

    public EmiOptionsResponse getEmiOptions(UUID productId) {
        ProductResponse product = productApiClient.getProduct(productId);
        validateProduct(productId, product);

        List<CreditProviderResponse> providers =
                creditProviderApiClient.getProviders(product.price());
        List<ProviderOptions> providerOptions = new ArrayList<>();

        for (CreditProviderResponse provider : providers) {
            if (!isEligible(provider, product.price())) {
                continue;
            }
            providerOptions.add(toProviderOptions(provider, product.price()));
        }

        return new EmiOptionsResponse(
                product.productId(),
                product.name(),
                product.currency(),
                product.price().setScale(2, RoundingMode.HALF_UP),
                List.copyOf(providerOptions));
    }

    private void validateProduct(UUID requestedProductId, ProductResponse product) {
        if (product.productId() == null || !requestedProductId.equals(product.productId())
                || product.price() == null
                || product.price().signum() <= 0
                || product.currency() == null || product.currency().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY, "Product service returned invalid product data");
        }
        if (!Boolean.TRUE.equals(product.inStock())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Product is not currently in stock");
        }
    }

    private boolean isEligible(CreditProviderResponse provider, BigDecimal price) {
        
        if (provider.minOrderAmount() != null && price.compareTo(provider.minOrderAmount()) < 0) {
            return false;
        }
        if (provider.maxOrderAmount() != null && price.compareTo(provider.maxOrderAmount()) > 0) {
            return false;
        }
        return provider.supportedTenures() != null
                && REQUESTED_TENURES.stream().anyMatch(provider.supportedTenures()::contains);
    }

    private ProviderOptions toProviderOptions(CreditProviderResponse provider, BigDecimal price) {
        if (provider.providerId() == null || provider.name() == null
                || provider.defaultInterestRate() == null
                || provider.defaultInterestRate().signum() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY, "Credit provider service returned invalid provider data");
        }

        BigDecimal processingFee = calculateProcessingFee(provider, price);
        List<EmiTerm> offers = REQUESTED_TENURES.stream()
                .filter(provider.supportedTenures()::contains)
                .map(tenure -> toEmiTerm(provider, price, tenure, processingFee))
                .toList();

        return new ProviderOptions(
                provider.providerId(),
                provider.name(),
                provider.code(),
                provider.noCostEmiSupported(),
                offers);
    }

    private EmiTerm toEmiTerm(
            CreditProviderResponse provider,
            BigDecimal price,
            int tenureMonths,
            BigDecimal processingFee) {
        BigDecimal monthlyEmi = emiCalculator.calculate(
                price, provider.defaultInterestRate(), tenureMonths);
        BigDecimal totalPayable = monthlyEmi.multiply(BigDecimal.valueOf(tenureMonths))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalInterest = totalPayable.subtract(price)
                .max(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);

        return new EmiTerm(
                tenureMonths,
                provider.defaultInterestRate(),
                monthlyEmi,
                totalInterest,
                totalPayable,
                processingFee);
    }

    private BigDecimal calculateProcessingFee(CreditProviderResponse provider, BigDecimal price) {
        if (provider.processingFee() == null || provider.processingFeeType() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY, "Credit provider service omitted processing fee details");
        }

        return switch (provider.processingFeeType().toUpperCase()) {
            case "FLAT" -> provider.processingFee().setScale(2, RoundingMode.HALF_UP);
            case "PERCENTAGE" -> price.multiply(provider.processingFee())
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY, "Unsupported processing fee type: "
                            + provider.processingFeeType());
        };
    }
}
