package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credit_providers", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class CreditProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "provider_type", length = 30)
    private String providerType;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "min_order_amount", precision = 10, scale = 2)
    private BigDecimal minOrderAmount;

    @Column(name = "max_order_amount", precision = 10, scale = 2)
    private BigDecimal maxOrderAmount;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "supported_tenures", columnDefinition = "integer[]")
    private Integer[] supportedTenures;

    @Column(name = "default_interest_rate", precision = 5, scale = 2)
    private BigDecimal defaultInterestRate;

    @Column(name = "no_cost_emi_supported")
    private Boolean noCostEmiSupported;

    @Column(name = "processing_fee", precision = 10, scale = 2)
    private BigDecimal processingFee;

    @Column(name = "processing_fee_type")
    private String processingFeeType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "gateway_config", columnDefinition = "jsonb")
    private Map<String, Object> gatewayConfig;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}