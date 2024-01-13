package com.juneox.marketspace.domain.model.meta.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ServiceIndustryDto {

    String serviceIndustryCode;
    String serviceIndustryCodeName;
}
