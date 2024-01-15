package com.juneox.marketspace.domain.raw;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MarketSpaceRawData {

    String yearAndQuarterCode;
    String marketSpaceGroupCode;
    String marketSpaceGroupCodeName;
    String marketSpaceCode;
    String marketSpaceCodeName;
    String serviceIndustryCode;
    String serviceIndustryCodeName;
    int storesNumber;
    int similarIndustryStoreNumber;
    int bizNewStoreRate;
    int bizNewStoreNumber;
    int bizCloseStoreRate;
    int bizCloseStoreNumber;
    int franchiseStoreNumber;

}
