package com.juneox.marketspace.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ServiceIndustriesWthStoreNumResponse {

    @JsonProperty("svc_industry_code")
    String serviceIndustryCode;
    @JsonProperty("svc_industry_code_name")
    String serviceIndustryCodeName;
    @JsonProperty("stores_number")
    private int storesNumber;
}
