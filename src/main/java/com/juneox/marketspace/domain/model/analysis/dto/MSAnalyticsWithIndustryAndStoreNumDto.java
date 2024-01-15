package com.juneox.marketspace.domain.model.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MSAnalyticsWithIndustryAndStoreNumDto {

    private String yearAndQuarterCode;
    private String serviceIndustryCodeName;
    private int storesNumber;
}