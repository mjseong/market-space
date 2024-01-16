package com.juneox.marketspace.persistence.qdsl;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;

import java.util.List;

public interface MarketSpaceAnalyticsQDSLRepository {

    public List<MSAnalyticsWithIndustryDto> findAllByYearQuarterCodes(List<String> yearAndQuarterCodes);
    public List<MSAnalyticsWithIndustryAndStoreNumDto> findAllTopRankByYearQuarterCodesAndMsCode(List<String> yearAndQuarterCodes,
                                                                                                 List<String> marketSpaceCodes);
    public List<MSAnalyticsWithIndustryAndCloseRateDto> findAllCloseRateByYearQuarterCodesAndMsCode(List<String> yearAndQuarterCodes,
                                                                                                       List<String> marketSpaceCodes);
}
