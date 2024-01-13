package com.juneox.marketspace.web.facade;

import com.juneox.marketspace.domain.model.analysis.dto.MarketSpaceAnalyticsDto;
import com.juneox.marketspace.domain.model.meta.cache.MetaCacheStore;
import com.juneox.marketspace.domain.model.meta.dto.MarketSpaceDto;
import com.juneox.marketspace.domain.model.meta.dto.MarketSpaceGroupDto;
import com.juneox.marketspace.domain.model.meta.dto.ServiceIndustryDto;
import com.juneox.marketspace.domain.model.meta.entity.MarketSpace;
import com.juneox.marketspace.domain.model.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.domain.model.meta.entity.ServiceIndustry;
import com.juneox.marketspace.domain.model.raw.MarketSpaceRawData;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import com.juneox.marketspace.service.meta.MarketSpaceGroupService;
import com.juneox.marketspace.service.meta.MarketSpaceService;
import com.juneox.marketspace.service.meta.ServiceIndustryService;
import com.juneox.marketspace.utils.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MetaDataFacadeService {

    private final MarketSpaceGroupService marketSpaceGroupService;
    private final MarketSpaceService marketSpaceService;
    private final ServiceIndustryService serviceIndustryService;
    private final MarketSpaceAnalyticsService marketSpaceAnalyticsService;

    public void loadMarketSpaceDataFile(String filePath){
        List<MarketSpaceRawData> rawData = this.parseMarketSpaceCsv(filePath);

        //insert marketSpaceGroup
        Map<String, MarketSpaceGroup> marketSpaceGroupMap = marketSpaceGroupService.createBulkMarketSpaceGroup(
                new ArrayList<>(MetaCacheStore.getInstance().getMarketSpaceGroupDtoMap().values()));

        //insert marketSpace
        Map<String, MarketSpace> marketSpaceMap = marketSpaceService.createBulkMarketSpace(
                new ArrayList<>(MetaCacheStore.getInstance().getMarketSpaceDtoMap().values()));

        //insert serviceIndustry
        Map<String, ServiceIndustry> serviceIndustryMap = serviceIndustryService.createBulkServiceIndustry(
                new ArrayList<>(MetaCacheStore.getInstance().getServiceIndustryDtoMap().values()));

        System.out.println(marketSpaceGroupMap.size());
        System.out.println(marketSpaceMap.size());
        System.out.println(serviceIndustryMap.size());

        //insert
        List<MarketSpaceAnalyticsDto> marketSpaceAnalyticsDtos = rawData.stream()
                .map(p-> MarketSpaceAnalyticsDto.builder()
                        .yearAndQuarterCode(p.getYearAndQuarterCode())
                        .marketSpaceGroup(marketSpaceGroupMap.get(p.getMarketSpaceGroupCode()))
                        .marketSpace(marketSpaceMap.get(p.getMarketSpaceCode()))
                        .serviceIndustry(serviceIndustryMap.get(p.getServiceIndustryCode()))
                        .storesNumber(p.getStoresNumber())
                        .similarIndustryStoreNumber(p.getSimilarIndustryStoreNumber())
                        .bizNewStoreRate(p.getBizNewStoreRate())
                        .bizNewStoreNumber(p.getBizNewStoreNumber())
                        .bizCloseStoreRate(p.getBizCloseStoreRate())
                        .bizCloseStoreNumber(p.getBizCloseStoreNumber())
                        .franchiseStoreNumber(p.getFranchiseStoreNumber())
                        .build()).collect(Collectors.toList());

        marketSpaceAnalyticsService.createBulkMarketSpaceAnalytics(marketSpaceAnalyticsDtos);

    }
    private List<MarketSpaceRawData> parseMarketSpaceCsv(String filePath){
        List<List<String>> records = CsvUtils.readCsv(filePath);

        List<MarketSpaceRawData> rawData = records.stream().skip(1)
                .map(row -> {
                    //cache marketSpaceGroupDto
                    MetaCacheStore.getInstance().putMarketSpaceGroupDto(MarketSpaceGroupDto.builder()
                                    .marketSpaceGroupCode(row.get(1))
                                    .marketSpaceGroupCodeName(row.get(2)).build());

                    //cache marketSpaceDto
                    MetaCacheStore.getInstance().putMarketSpaceDto(MarketSpaceDto.builder()
                                    .marketSpaceCode(row.get(3))
                                    .marketSpaceCodeName(row.get(4)).build());

                    //cache serviceIndustryDto
                    MetaCacheStore.getInstance().putServiceIndustryDto(ServiceIndustryDto.builder()
                                    .serviceIndustryCode(row.get(5))
                                    .serviceIndustryCodeName(row.get(6)).build());

                    return MarketSpaceRawData.builder()
                            .yearAndQuarterCode(row.get(0))
                            .marketSpaceGroupCode(row.get(1))
                            .marketSpaceGroupCodeName(row.get(2))
                            .marketSpaceCode(row.get(3))
                            .marketSpaceCodeName(row.get(4))
                            .serviceIndustryCode(row.get(5))
                            .serviceIndustryCodeName(row.get(6))
                            .storesNumber(Integer.parseInt(row.get(7)))
                            .similarIndustryStoreNumber(Integer.parseInt(row.get(8)))
                            .bizNewStoreRate(Integer.parseInt(row.get(9)))
                            .bizNewStoreNumber(Integer.parseInt(row.get(10)))
                            .bizCloseStoreRate(Integer.parseInt(row.get(11)))
                            .bizCloseStoreNumber(Integer.parseInt(row.get(12)))
                            .franchiseStoreNumber(Integer.parseInt(row.get(13)))
                            .build();
                })
                .collect(Collectors.toList());

        return rawData;
    }


}
