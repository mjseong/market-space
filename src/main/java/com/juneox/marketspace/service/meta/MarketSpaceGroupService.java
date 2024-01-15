package com.juneox.marketspace.service.meta;

import com.juneox.marketspace.domain.meta.dto.MarketSpaceGroupDto;
import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;

import java.util.List;
import java.util.Map;

public interface MarketSpaceGroupService {

    public MarketSpaceGroup getMarketSpaceGroup();

    public MarketSpaceGroup createMarketSpaceGroup(MarketSpaceGroupDto marketSpaceGroupDto);
    public Map<String, MarketSpaceGroup> createBulkMarketSpaceGroup(List<MarketSpaceGroupDto> marketSpaceGroupDtos);
    public MarketSpaceGroup modifyMarketSpaceGroup();
    public void deleteMarketSpaceGroup(long id);
}
