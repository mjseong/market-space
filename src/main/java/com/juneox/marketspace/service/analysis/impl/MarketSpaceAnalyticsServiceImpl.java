package com.juneox.marketspace.service.analysis.impl;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.domain.analysis.dto.MarketSpaceAnalyticsDto;
import com.juneox.marketspace.domain.analysis.entity.MarketSpaceAnalytics;
import com.juneox.marketspace.persistence.jdbc.AnalyticsDAO;
import com.juneox.marketspace.persistence.jpa.MarketSpaceAnalyticsRepository;
import com.juneox.marketspace.persistence.qdsl.AnalyticsQDSLRepository;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MarketSpaceAnalyticsServiceImpl implements MarketSpaceAnalyticsService {

    private final MarketSpaceAnalyticsRepository marketSpaceAnalyticsRepository;
    private final AnalyticsQDSLRepository analyticsQDSLRepository;
    private final AnalyticsDAO analyticsDAO;

    @Transactional(propagation = Propagation.REQUIRED)
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

    @Override
    public Set<String> getDistinctYearAndQuarterCode() {
        List<String> distinctYearAndQuarterCode =
                marketSpaceAnalyticsRepository.findAllByDistinctYearAndQuarterCode();

        return distinctYearAndQuarterCode.stream()
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public List<MSAnalyticsWithIndustryDto> getMSAnalyticsWithIndustryNames(List<String> yearAndQuarters) {
        List<MSAnalyticsWithIndustryDto> results = analyticsDAO.findAllByYearAndQuarterCodes(yearAndQuarters);
        return results;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MSAnalyticsWithIndustryAndStoreNumDto> getMSAnalyticsWithTopRankIndustryNames(List<String> yearAndQuarters, List<String> marketSpaceCodes) {

        List<MSAnalyticsWithIndustryAndStoreNumDto> results =
                analyticsQDSLRepository.findAllTopRankByYearQuarterCodesAndMsCode(yearAndQuarters, marketSpaceCodes);
        return results;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MSAnalyticsWithIndustryAndCloseRateDto> getMSAnalyticsWithLowCloseRateIndustryNames(List<String> yearAndQuarters, List<String> marketSpaceCodes) {

        List<MSAnalyticsWithIndustryAndCloseRateDto> topLowCloseRateIndustries =
                analyticsDAO.findAllCloseRateByYearQuarterCodesAndMsCode(yearAndQuarters, marketSpaceCodes);

        return topLowCloseRateIndustries;
    }
}
