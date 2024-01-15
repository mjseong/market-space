package com.juneox.marketspace.domain.analysis.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class MSAnalyticsWithIndustryDto {

    private String yearAndQuarterCode;
    private String serviceIndustryCodeName;

    public MSAnalyticsWithIndustryDto(String yearAndQuarterCode, String serviceIndustryCodeName) {
        this.yearAndQuarterCode = yearAndQuarterCode;
        this.serviceIndustryCodeName = serviceIndustryCodeName;
    }

}
