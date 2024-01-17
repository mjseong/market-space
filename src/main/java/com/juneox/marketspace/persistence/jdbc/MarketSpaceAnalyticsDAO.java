package com.juneox.marketspace.persistence.jdbc;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.domain.analysis.entity.MarketSpaceAnalytics;

import java.util.List;

public interface MarketSpaceAnalyticsDAO {
    public List<MSAnalyticsWithIndustryDto> findAllByYearAndQuarterCodes(List<String> yearAndQuarterCodes);
    public List<MSAnalyticsWithIndustryAndCloseRateDto> findAllCloseRateByYearQuarterCodesAndMsCode(List<String> yearAndQuarterCodes,
                                                                                                    List<String> marketSpaceCodes);
    public void saveAll(List<MarketSpaceAnalytics> marketSpaceAnalyticsList);
}
