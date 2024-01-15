package com.juneox.marketspace.domain.analysis.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class MSAnalyticsWithIndustryAndCloseRateDto extends MSAnalyticsWithIndustryDto {

    private int bizCloseStoreRate;

    public MSAnalyticsWithIndustryAndCloseRateDto(String yearAndQuarterCode, String serviceIndustryCodeName, int bizCloseStoreRate) {
        super(yearAndQuarterCode, serviceIndustryCodeName);
        this.bizCloseStoreRate = bizCloseStoreRate;
    }
}
