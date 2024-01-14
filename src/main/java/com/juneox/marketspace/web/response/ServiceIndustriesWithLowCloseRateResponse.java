package com.juneox.marketspace.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ServiceIndustriesWithLowCloseRateResponse {
    @JsonProperty("svc_industry_code")
    private String serviceIndustryCode;
    @JsonProperty("svc_industry_code_name")
    private String serviceIndustryCodeName;
    @JsonProperty("biz_close_store_rate")
    private int bizCloseStoreRate;
}
