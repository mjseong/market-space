package com.juneox.marketspace.persistence.jdbc;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;

import java.util.List;

public interface AnalyticsJdbcRepository {
    public List<MSAnalyticsWithIndustryDto> findAllByYearAndQuarterCodes(List<String> yearAndQuarterCodes);
}
