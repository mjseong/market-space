package com.juneox.marketspace.persistence.jpa;

import com.juneox.marketspace.domain.analysis.entity.MarketSpaceAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarketSpaceAnalyticsRepository extends JpaRepository<MarketSpaceAnalytics, Long> {
    @Query("""
        SELECT DISTINCT msa.yearAndQuarterCode
        FROM MarketSpaceAnalytics msa
    """)
    List<String> findAllByDistinctYearAndQuarterCode();
}
