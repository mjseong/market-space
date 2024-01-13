package com.juneox.marketspace.domain.model.meta.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MarketSpaceGroupDto {

    String marketSpaceGroupCode;
    String marketSpaceGroupCodeName;
}
