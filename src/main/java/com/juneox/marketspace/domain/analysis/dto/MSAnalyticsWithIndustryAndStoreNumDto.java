package com.juneox.marketspace.domain.analysis.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class MSAnalyticsWithIndustryAndStoreNumDto extends MSAnalyticsWithIndustryDto{

    private int storesNumber;

    public MSAnalyticsWithIndustryAndStoreNumDto(String yearAndQuarterCode, String serviceIndustryCodeName, int storesNumber) {
        super(yearAndQuarterCode, serviceIndustryCodeName);
        this.storesNumber = storesNumber;
    }
}
