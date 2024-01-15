package com.juneox.marketspace.service.analysis;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.domain.analysis.dto.MarketSpaceAnalyticsDto;

import java.util.List;

public interface MarketSpaceAnalyticsService {

    public void createBulkMarketSpaceAnalytics(List<MarketSpaceAnalyticsDto> marketSpaceAnalyticsDtos);
    public List<MSAnalyticsWithIndustryDto> getMSAnalyticsWithIndustryNames(List<String> yearAndQuarters);
    public List<MSAnalyticsWithIndustryAndStoreNumDto> getMSAnalyticsWithTopRankIndustryNames(List<String> yearAndQuarters,
                                                                                              List<String> marketSpaceCodes);
    public List<MSAnalyticsWithIndustryAndCloseRateDto> getMSAnalyticsWithLowCloseRateIndustryNames(List<String> yearAndQuarters,
                                                                                                    List<String> marketSpaceCodes);
}
