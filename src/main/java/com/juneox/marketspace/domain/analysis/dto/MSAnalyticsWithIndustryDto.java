package com.juneox.marketspace.domain.analysis.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class MSAnalyticsWithIndustryDto {

    private String serviceIndustryCodeName;

    public MSAnalyticsWithIndustryDto(String serviceIndustryCodeName) {
        this.serviceIndustryCodeName = serviceIndustryCodeName;
    }

}
