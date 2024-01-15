package com.juneox.marketspace.domain.meta.cache;

import com.juneox.marketspace.domain.meta.dto.MarketSpaceDto;
import com.juneox.marketspace.domain.meta.dto.MarketSpaceGroupDto;
import com.juneox.marketspace.domain.meta.dto.ServiceIndustryDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MetaCacheStore {

    private final ConcurrentHashMap<String, MarketSpaceGroupDto> marketSpaceGroupDtoMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, MarketSpaceDto> marketSpaceDtoMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, ServiceIndustryDto> serviceIndustryDtoMap = new ConcurrentHashMap();

    private static class SingletonHolder{
        static final MetaCacheStore _INSTANCE = new MetaCacheStore();
    }

    public static MetaCacheStore getInstance(){
        return SingletonHolder._INSTANCE;
    }

    public void putMarketSpaceGroupDto(MarketSpaceGroupDto marketSpaceGroupDto){
        marketSpaceGroupDtoMap.putIfAbsent(marketSpaceGroupDto.getMarketSpaceGroupCode(), marketSpaceGroupDto);
    }

    public void putMarketSpaceDto(MarketSpaceDto marketSpaceDto){
        marketSpaceDtoMap.putIfAbsent(marketSpaceDto.getMarketSpaceCode(), marketSpaceDto);
    }

    public void putServiceIndustryDto(ServiceIndustryDto serviceIndustryDto){
        serviceIndustryDtoMap.putIfAbsent(serviceIndustryDto.getServiceIndustryCode(), serviceIndustryDto);
    }

    public Map<String, MarketSpaceDto> getMarketSpaceDtoMap(){
        return marketSpaceDtoMap;
    }

    public Map<String, MarketSpaceGroupDto> getMarketSpaceGroupDtoMap(){
        return marketSpaceGroupDtoMap;
    }

    public Map<String, ServiceIndustryDto> getServiceIndustryDtoMap(){
        return serviceIndustryDtoMap;
    }



}
