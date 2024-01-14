package com.juneox.marketspace.web.facade;

import com.juneox.marketspace.domain.model.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.model.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.model.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import com.juneox.marketspace.web.response.ServiceIndustriesResponse;
import com.juneox.marketspace.web.response.ServiceIndustriesWithLowCloseRateResponse;
import com.juneox.marketspace.web.response.ServiceIndustriesWthStoreNumResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MarketSpaceAnalyticsFacadeService {

    private final MarketSpaceAnalyticsService marketSpaceAnalyticsService;

    @Transactional(readOnly = true)
    public List<ServiceIndustriesResponse> getIndustryNamesByYearQuarter(List<String> yearAndQuarterCodes){
        List<MSAnalyticsWithIndustryDto> results =
                marketSpaceAnalyticsService.getMSAnalyticsWithIndustryNames(yearAndQuarterCodes);
        if(!results.isEmpty()) {
            return results.stream()
                    .map(p -> ServiceIndustriesResponse.builder()
                            .serviceIndustryCode(p.getYearAndQuarterCode())
                            .serviceIndustryCodeName(p.getServiceIndustryCodeName())
                            .build())
                    .collect(Collectors.toList());
        }

        return null;
    }

    @Transactional(readOnly = true)
    public List<ServiceIndustriesWthStoreNumResponse> getTopRankIndustryNames(List<String> yearAndQuarterCodes,
                                                                   List<String> marketSpaceCodes){
        List<MSAnalyticsWithIndustryAndStoreNumDto> results =
                marketSpaceAnalyticsService.getMSAnalyticsWithTopRankIndustryNames(yearAndQuarterCodes, marketSpaceCodes);

        if(!results.isEmpty()){
            return results.stream()
                    .map(p-> ServiceIndustriesWthStoreNumResponse.builder()
                            .serviceIndustryCode(p.getYearAndQuarterCode())
                            .serviceIndustryCodeName(p.getServiceIndustryCodeName())
                            .storesNumber(p.getStoresNumber())
                            .build())
                    .collect(Collectors.toList());
        }

        return null;
    }

    @Transactional(readOnly = true)
    public List<ServiceIndustriesWithLowCloseRateResponse> getLowCloseRateStoreIndustryNames(List<String> yearAndQuarterCodes,
                                                                             List<String> marketSpaceCodes) {
        List<MSAnalyticsWithIndustryAndCloseRateDto> results =
                marketSpaceAnalyticsService.getMSAnalyticsWithLowCloseRateIndustryNames(yearAndQuarterCodes, marketSpaceCodes);

        if(!results.isEmpty()){
            return results.stream()
                    .map(p-> ServiceIndustriesWithLowCloseRateResponse.builder()
                            .serviceIndustryCode(p.getYearAndQuarterCode())
                            .serviceIndustryCodeName(p.getServiceIndustryCodeName())
                            .bizCloseStoreRate(p.getBizCloseStoreRate())
                            .build())
                    .collect(Collectors.toList());
        }

        return null;
    }

}
