package com.juneox.marketspace.domain.model.analysis.dto;

import com.juneox.marketspace.domain.model.meta.entity.MarketSpace;
import com.juneox.marketspace.domain.model.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.domain.model.meta.entity.ServiceIndustry;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MarketSpaceAnalyticsDto {
    String yearAndQuarterCode;
    int storesNumber;
    int similarIndustryStoreNumber;
    int bizNewStoreRate;
    int bizNewStoreNumber;
    int bizCloseStoreRate;
    int bizCloseStoreNumber;
    int franchiseStoreNumber;
    MarketSpaceGroup marketSpaceGroup;
    MarketSpace marketSpace;
    ServiceIndustry serviceIndustry;
}