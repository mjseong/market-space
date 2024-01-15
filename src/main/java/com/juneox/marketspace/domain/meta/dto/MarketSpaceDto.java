package com.juneox.marketspace.domain.meta.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MarketSpaceDto {

    String marketSpaceCode;
    String marketSpaceCodeName;
}
