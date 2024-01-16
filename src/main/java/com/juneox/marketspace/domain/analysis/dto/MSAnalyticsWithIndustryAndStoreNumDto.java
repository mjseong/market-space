package com.juneox.marketspace.domain.analysis.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class MSAnalyticsWithIndustryAndStoreNumDto extends MSAnalyticsWithIndustryDto{

    private String yearAndQuarterCode;
    private int storesNumber;

    public MSAnalyticsWithIndustryAndStoreNumDto(String serviceIndustryCodeName, String yearAndQuarterCode, int storesNumber) {
        super(serviceIndustryCodeName);
        this.yearAndQuarterCode = yearAndQuarterCode;
        this.storesNumber = storesNumber;
    }
}
