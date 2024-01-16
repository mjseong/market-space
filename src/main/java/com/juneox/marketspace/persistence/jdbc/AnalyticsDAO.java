package com.juneox.marketspace.persistence.jdbc;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;

import java.util.List;

public interface AnalyticsDAO {
    public List<MSAnalyticsWithIndustryDto> findAllByYearAndQuarterCodes(List<String> yearAndQuarterCodes);
    public List<MSAnalyticsWithIndustryAndCloseRateDto> findAllCloseRateByYearQuarterCodesAndMsCode(List<String> yearAndQuarterCodes,
                                                                                                    List<String> marketSpaceCodes);
}
