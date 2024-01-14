package com.juneox.marketspace.domain.model.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MSAnalyticsWithIndustryDto {

    private String yearAndQuarterCode;
    private String serviceIndustryCodeName;

}
