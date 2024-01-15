package com.juneox.marketspace.domain.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MSAnalyticsWithIndustryDto {

    private String yearAndQuarterCode;
    private String serviceIndustryCodeName;

}
