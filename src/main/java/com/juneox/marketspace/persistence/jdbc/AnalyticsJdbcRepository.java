package com.juneox.marketspace.persistence.jdbc;

import com.juneox.marketspace.domain.model.analysis.dto.MSAnalyticsWithIndustryDto;

import java.util.List;

public interface AnalyticsJdbcRepository {
    public List<MSAnalyticsWithIndustryDto> findAllByYearAndQuarterCodes(List<String> yearAndQuarterCodes);
}
