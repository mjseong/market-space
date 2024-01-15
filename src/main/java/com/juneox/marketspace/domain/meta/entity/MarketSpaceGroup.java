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
@Table(name = "market_space_group")
@Entity
public class MarketSpaceGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ms_group_id")
    Long id;

    @Column(name = "ms_group_code")
    String marketSpaceGroupCode;
    @Column(name = "ms_group_code_name")
    String marketSpaceGroupCodeName;

    @Column(name = "created_at")
    @CreationTimestamp
    Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    Instant updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "marketSpaceGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<MarketSpaceAnalytics> marketSpaceAnalytics;
}
