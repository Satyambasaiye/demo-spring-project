package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class EmiCalculatorTest {
    private final EmiCalculator calculator = new EmiCalculator();

    @Test
    void calculatesZeroInterestEmi() {
        BigDecimal emi = calculator.calculate(new BigDecimal("1000"), BigDecimal.ZERO, 3);

        assertEquals(new BigDecimal("333.33"), emi);
    }

    @Test
    void calculatesReducingBalanceEmi() {
        BigDecimal emi = calculator.calculate(new BigDecimal("1200"), new BigDecimal("12"), 12);

        assertEquals(new BigDecimal("106.62"), emi);
    }
}
