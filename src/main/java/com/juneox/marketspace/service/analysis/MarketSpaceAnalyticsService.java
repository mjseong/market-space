package com.juneox.marketspace.service.analysis;

import com.juneox.marketspace.domain.model.analysis.dto.MarketSpaceAnalyticsDto;

import java.util.List;

public interface MarketSpaceAnalyticsService {

    public void createBulkMarketSpaceAnalytics(List<MarketSpaceAnalyticsDto> marketSpaceAnalyticsDtos);
}
