package com.juneox.marketspace.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ServiceIndustriesResponse {
//    @JsonProperty("svc_industry_code")
//    String serviceIndustryCode;
    @JsonProperty("svc_industry_code_name")
    String serviceIndustryCodeName;
}
