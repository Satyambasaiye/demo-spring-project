package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.example.demo.constants.KromaConstants;


@Entity
@Table(name="credit_providers")
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditProviders {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "provider_id", updatable = false, nullable = false)
    private UUID providerId;
    
    // Core Provider Details
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "code", length = 50, nullable = false, unique = true)
    private String code;

    @Column(name = "provider_type", length = 30, nullable = false)
    private String providerType;

    @Column(name = "logo_url", columnDefinition = "TEXT")
    private String logoUrl;

    // Transaction & Tenure Rules
    @Builder.Default
    @Column(name = "min_order_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal minOrderAmount = new BigDecimal("3000.00");

    @Column(name = "max_order_amount", precision = 10, scale = 2)
    private BigDecimal maxOrderAmount;

    @Builder.Default
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "supported_tenures", columnDefinition = "int[]", nullable = false)
    private List<Integer> supportedTenures = List.of(3, 6, 9, 12);

    // Interest & Fee Defaults
    @Builder.Default
    @Column(name = "default_interest_rate", precision = 5, scale = 2, nullable = false)
    private BigDecimal defaultInterestRate = new BigDecimal("15.00");

    @Builder.Default
    @Column(name = "no_cost_emi_supported", nullable = false)
    private Boolean noCostEmiSupported = false;

    @Builder.Default
    @Column(name = "processing_fee", precision = 10, scale = 2, nullable = false)
    private BigDecimal processingFee = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "processing_fee_type", length = 15, nullable = false)
    private String processingFeeType = KromaConstants.ProcessingFeeTypeF;

    // Gateway Integration & Status
    @Builder.Default
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "gateway_config", columnDefinition = "jsonb")
    private Map<String, Object> gatewayConfig = new HashMap<>();

    @Builder.Default
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Audit Timestamps
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
