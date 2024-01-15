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
@Table(name = "market_space_info")
@Entity
public class MarketSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ms_info_id")
    Long id;

    @Column(name = "ms_code")
    String marketSpaceCode;

    @Column(name = "ms_code_name")
    String marketSpaceCodeName;

    @Column(name = "created_at")
    @CreationTimestamp
    Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    Instant updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "marketSpace", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<MarketSpaceAnalytics> marketSpaceAnalytics;

}
