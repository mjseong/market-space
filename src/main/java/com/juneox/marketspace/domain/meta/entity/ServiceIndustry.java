package com.juneox.marketspace.domain.meta.entity;

import com.juneox.marketspace.domain.analysis.entity.MarketSpaceAnalytics;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "service_industry_info")
@Entity
public class ServiceIndustry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "si_info_id")
    Long id;

    @Column(name = "si_code")
    String serviceIndustryCode;
    @Column(name = "si_code_name")
    String serviceIndustryCodeName;

    @Column(name = "created_at")
    @CreationTimestamp
    Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    Instant updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceIndustry", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<MarketSpaceAnalytics> marketSpaceAnalytics;
}
