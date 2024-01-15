package com.juneox.marketspace.service.meta;

import com.juneox.marketspace.domain.meta.dto.MarketSpaceDto;
import com.juneox.marketspace.domain.meta.entity.MarketSpace;

import java.util.List;
import java.util.Map;

public interface MarketSpaceService {

    public MarketSpace createMarketSpace(MarketSpaceDto marketSpaceDto);
    public Map<String, MarketSpace> createBulkMarketSpace(List<MarketSpaceDto> marketSpaceDtos);
    public MarketSpace modifyMarketSpace();
    public void deleteMarketSpace(long id);

}
