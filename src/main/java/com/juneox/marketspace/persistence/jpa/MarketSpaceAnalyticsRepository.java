package com.juneox.marketspace.persistence.jpa;

import com.juneox.marketspace.domain.model.analysis.entity.MarketSpaceAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketSpaceAnalyticsRepository extends JpaRepository<MarketSpaceAnalytics, Long> {
}
