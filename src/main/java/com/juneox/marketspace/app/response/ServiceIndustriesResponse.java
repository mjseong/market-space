package com.juneox.marketspace.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceIndustriesResponse {
    @JsonProperty("svc_industry_code")
    String serviceIndustryCode;
    @JsonProperty("svc_industry_code_name")
    String serviceIndustryCodeName;
}
