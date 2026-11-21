package com.example.demo.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

@Component
public class EmiCalculator {
    private static final BigDecimal TWELVE_HUNDRED = new BigDecimal("1200");
    private static final MathContext CALCULATION_CONTEXT = new MathContext(24, RoundingMode.HALF_UP);

    public BigDecimal calculate(BigDecimal principal, BigDecimal annualInterestRate, int tenureMonths) {
        if (principal == null || principal.signum() <= 0) {
            throw new IllegalArgumentException("Principal must be greater than zero");
        }
        if (annualInterestRate == null || annualInterestRate.signum() < 0) {
            throw new IllegalArgumentException("Annual interest rate cannot be negative");
        }
        if (tenureMonths <= 0) {
            throw new IllegalArgumentException("Tenure must be greater than zero");
        }

        if (annualInterestRate.signum() == 0) {
            return principal.divide(BigDecimal.valueOf(tenureMonths), 2, RoundingMode.HALF_UP);
        }

        BigDecimal monthlyRate = annualInterestRate.divide(TWELVE_HUNDRED, CALCULATION_CONTEXT);
        BigDecimal factor = BigDecimal.ONE.add(monthlyRate)
                .pow(tenureMonths, CALCULATION_CONTEXT);
        BigDecimal numerator = principal.multiply(monthlyRate, CALCULATION_CONTEXT)
                .multiply(factor, CALCULATION_CONTEXT);
        BigDecimal denominator = factor.subtract(BigDecimal.ONE, CALCULATION_CONTEXT);

        return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
    }
}
