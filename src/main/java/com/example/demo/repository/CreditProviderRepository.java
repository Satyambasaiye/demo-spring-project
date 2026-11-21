package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.CreditProviders;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;


public interface CreditProviderRepository
        extends JpaRepository<CreditProviders, UUID> {
            @Query("""
        SELECT c FROM CreditProviders c
        WHERE c.isActive = true
          AND c.minOrderAmount <= :amount
          AND (c.maxOrderAmount IS NULL OR c.maxOrderAmount >= :amount)
        ORDER BY c.displayOrder ASC
    """)
    List<CreditProviders> findEligibleProviders(@Param("amount") BigDecimal orderAmount);
    @Override
    List<CreditProviders> findAll();
}