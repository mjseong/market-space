package com.juneox.marketspace.service.analysis.impl;

import com.juneox.marketspace.domain.model.analysis.dto.MarketSpaceAnalyticsDto;
import com.juneox.marketspace.domain.model.analysis.entity.MarketSpaceAnalytics;
import com.juneox.marketspace.persistence.jpa.MarketSpaceAnalyticsRepository;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MarketSpaceAnalyticsServiceImpl implements MarketSpaceAnalyticsService {

    private final MarketSpaceAnalyticsRepository marketSpaceAnalyticsRepository;

    @Transactional
    @Override
    public void createBulkMarketSpaceAnalytics(List<MarketSpaceAnalyticsDto> marketSpaceAnalyticsDtos) {

        List<MarketSpaceAnalytics> marketSpaceAnalytics = marketSpaceAnalyticsDtos.stream()
                .map(p-> MarketSpaceAnalytics.builder()
                        .yearAndQuarterCode(p.getYearAndQuarterCode())
                        .marketSpaceGroup(p.getMarketSpaceGroup())
                        .marketSpace(p.getMarketSpace())
                        .serviceIndustry(p.getServiceIndustry())
                        .storesNumber(p.getStoresNumber())
                        .similarIndustryStoreNumber(p.getSimilarIndustryStoreNumber())
                        .bizNewStoreRate(p.getBizNewStoreRate())
                        .bizNewStoreNumber(p.getBizNewStoreNumber())
                        .bizCloseStoreRate(p.getBizCloseStoreRate())
                        .bizCloseStoreNumber(p.getBizCloseStoreNumber())
                        .franchiseStoreNumber(p.getFranchiseStoreNumber())
                        .build())
                .collect(Collectors.toList());

        marketSpaceAnalyticsRepository.saveAll(marketSpaceAnalytics);
    }
}
