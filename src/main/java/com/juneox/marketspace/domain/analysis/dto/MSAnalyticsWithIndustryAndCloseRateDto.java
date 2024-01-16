package com.juneox.marketspace.domain.analysis.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class MSAnalyticsWithIndustryAndCloseRateDto extends MSAnalyticsWithIndustryDto {

    private String yearAndQuarterCode;
    private String marketSpaceCode;
    private int bizCloseStoreRate;

    public MSAnalyticsWithIndustryAndCloseRateDto(String serviceIndustryCodeName, String yearAndQuarterCode, String marketSpaceCode, int bizCloseStoreRate) {
        super(serviceIndustryCodeName);
        this.yearAndQuarterCode = yearAndQuarterCode;
        this.marketSpaceCode = marketSpaceCode;
        this.bizCloseStoreRate = bizCloseStoreRate;
    }
}
