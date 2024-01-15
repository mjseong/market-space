package com.juneox.marketspace.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ServiceIndustryNamesResponse {
    @JsonProperty("svc_industry_names")
    List<String> serviceIndustryNames;
}
