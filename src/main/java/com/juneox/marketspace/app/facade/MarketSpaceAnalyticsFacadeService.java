package com.juneox.marketspace.app.facade;

import com.juneox.marketspace.domain.model.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.model.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.model.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import com.juneox.marketspace.app.response.ServiceIndustriesResponse;
import com.juneox.marketspace.app.response.ServiceIndustriesWithLowCloseRateResponse;
import com.juneox.marketspace.app.response.ServiceIndustriesWthStoreNumResponse;
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
                            .serviceIndustryCodeName(p.getServiceIndustryCodeName())
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
                            .serviceIndustryCodeName(p.getServiceIndustryCodeName())
                            .build())
                    .collect(Collectors.toList());
        }

        return null;
    }

}
