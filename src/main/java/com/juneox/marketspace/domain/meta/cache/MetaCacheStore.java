package com.juneox.marketspace.domain.meta.cache;

import com.juneox.marketspace.domain.meta.dto.MarketSpaceDto;
import com.juneox.marketspace.domain.meta.dto.MarketSpaceGroupDto;
import com.juneox.marketspace.domain.meta.dto.ServiceIndustryDto;
import com.juneox.marketspace.domain.meta.entity.MarketSpace;
import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.domain.meta.entity.ServiceIndustry;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MetaCacheStore {

    private final ConcurrentHashMap<String, String> yearAndQuarterMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, MarketSpaceGroupDto> marketSpaceGroupDtoMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, MarketSpaceDto> marketSpaceDtoMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, ServiceIndustryDto> serviceIndustryDtoMap = new ConcurrentHashMap();

    private static class SingletonHolder{
        static final MetaCacheStore _INSTANCE = new MetaCacheStore();
    }

    public static MetaCacheStore getInstance(){
        return SingletonHolder._INSTANCE;
    }

    public void putYearAndQuarter(String yearAndQuarterCode){
        yearAndQuarterMap.putIfAbsent(yearAndQuarterCode, yearAndQuarterCode);
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

    public void putAllYearAndQuarterCode(Set<String> yearAndQuarters){
        yearAndQuarters.forEach(p->yearAndQuarterMap.putIfAbsent(p,p));
    }

    public void putAllMarketSpaceGroup(List<MarketSpaceGroup> marketSpaceGroups){
        marketSpaceGroups.forEach(p->
                marketSpaceGroupDtoMap.putIfAbsent(p.getMarketSpaceGroupCode(),
                        MarketSpaceGroupDto.builder()
                                .marketSpaceGroupCode(p.getMarketSpaceGroupCode())
                                .marketSpaceGroupCodeName(p.getMarketSpaceGroupCodeName()).build()));
    }

    public void putAllMarketSpace(List<MarketSpace> marketSpaces){
        marketSpaces.forEach(p->
                marketSpaceDtoMap.putIfAbsent(p.getMarketSpaceCode(),
                        MarketSpaceDto.builder()
                                .marketSpaceCode(p.getMarketSpaceCode())
                                .marketSpaceCodeName(p.getMarketSpaceCodeName()).build()));
    }
    public void putAllServiceIndustry(List<ServiceIndustry> serviceIndustries){
        serviceIndustries.forEach(p->
                serviceIndustryDtoMap.putIfAbsent(p.getServiceIndustryCode(),
                        ServiceIndustryDto.builder()
                                .serviceIndustryCode(p.getServiceIndustryCode())
                                .serviceIndustryCodeName(p.getServiceIndustryCodeName()).build()));
    }

    public Map<String, String> getYearAndQuarterMap(){
        return this.yearAndQuarterMap;
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

    public boolean existYearAndQuarter(String key){
        return this.yearAndQuarterMap.containsKey(key);
    }
    public boolean existMarketSpace(String key){
        return this.marketSpaceDtoMap.containsKey(key);
    }
    public boolean existMarketSpaceGroup(String key){
        return this.marketSpaceGroupDtoMap.containsKey(key);
    }

    public boolean existServiceIndustry(String key){
        return this.serviceIndustryDtoMap.containsKey(key);
    }

}
