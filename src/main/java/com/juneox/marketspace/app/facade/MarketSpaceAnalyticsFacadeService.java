package com.juneox.marketspace.app.facade;

import com.juneox.marketspace.app.response.ServiceIndustriesWithLowCloseRateResponse;
import com.juneox.marketspace.app.response.ServiceIndustryNamesResponse;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.domain.exception.CommonException;
import com.juneox.marketspace.domain.exception.type.ErrorMessageType;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
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
    public ServiceIndustryNamesResponse getIndustryNamesByYearQuarter(List<String> yearAndQuarterCodes){
        List<MSAnalyticsWithIndustryDto> results =
                marketSpaceAnalyticsService.getMSAnalyticsWithIndustryNames(yearAndQuarterCodes);
        if(!results.isEmpty()) {
            var list = results.stream()
                    .map(p-> p.getServiceIndustryCodeName())
                    .collect(Collectors.toList());

            return ServiceIndustryNamesResponse.builder()
                    .serviceIndustryNames(list)
                    .build();
        }else{
            throw new CommonException(ErrorMessageType.NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public ServiceIndustryNamesResponse getTopRankIndustryNames(List<String> yearAndQuarterCodes,
                                                                List<String> marketSpaceCodes){
        List<MSAnalyticsWithIndustryAndStoreNumDto> results =
                marketSpaceAnalyticsService.getMSAnalyticsWithTopRankIndustryNames(yearAndQuarterCodes, marketSpaceCodes);

        if(!results.isEmpty()){
            var list = results.stream()
                    .map(p-> p.getServiceIndustryCodeName())
                    .collect(Collectors.toList());

            return ServiceIndustryNamesResponse.builder()
                    .serviceIndustryNames(list)
                    .build();
        }else{
            throw new CommonException(ErrorMessageType.NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public List<ServiceIndustriesWithLowCloseRateResponse> getLowCloseRateStoreIndustryNames(List<String> yearAndQuarterCodes,
                                                                                       List<String> marketSpaceCodes) {
        List<MSAnalyticsWithIndustryAndCloseRateDto> results =
                marketSpaceAnalyticsService.getMSAnalyticsWithLowCloseRateIndustryNames(yearAndQuarterCodes, marketSpaceCodes);

        if(!results.isEmpty()){
            return results.stream()
                    .map(p-> ServiceIndustriesWithLowCloseRateResponse.builder()
                            .yearAndQuarter(p.getYearAndQuarterCode())
                            .marketSpaceCode(p.getMarketSpaceCode())
                            .serviceIndustryCodeName(p.getServiceIndustryCodeName())
                            .bizCloseStoreRate(p.getBizCloseStoreRate())
                            .build()
                    )
                    .collect(Collectors.toList());
        }else{
            throw new CommonException(ErrorMessageType.NOT_FOUND);
        }
    }

}
