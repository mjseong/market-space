package com.juneox.marketspace.test.app;

import com.juneox.marketspace.app.facade.MarketSpaceAnalyticsFacadeService;
import com.juneox.marketspace.app.response.ServiceIndustriesWithLowCloseRateResponse;
import com.juneox.marketspace.app.response.ServiceIndustryNamesResponse;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class MarketSpaceAnalyticsFacadeServiceTests {

    @MockBean
    MarketSpaceAnalyticsService marketSpaceAnalyticsService;
    MarketSpaceAnalyticsFacadeService marketSpaceAnalyticsFacadeService;

    @BeforeEach
    void init(){
        this.marketSpaceAnalyticsFacadeService = new MarketSpaceAnalyticsFacadeService(marketSpaceAnalyticsService);
    }

    @Test
    void getIndustryNamesByYearQuarterTest(){

        //given
        List<MSAnalyticsWithIndustryDto> resultDto = Arrays.asList(
                MSAnalyticsWithIndustryDto.builder()
                        .serviceIndustryCodeName("청과상").build(),
                MSAnalyticsWithIndustryDto.builder()
                        .serviceIndustryCodeName("육류판매").build(),
                MSAnalyticsWithIndustryDto.builder()
                        .serviceIndustryCodeName("핸드폰").build()
        );

        BDDMockito.given(marketSpaceAnalyticsService.getMSAnalyticsWithIndustryNames(Mockito.any()))
                .willReturn(resultDto);

        //when
        ServiceIndustryNamesResponse response =
                marketSpaceAnalyticsFacadeService.getIndustryNamesByYearQuarter(Arrays.asList("20221","20222"));

        //then
        Assertions.assertEquals(List.of("청과상","육류판매","핸드폰").get(0),
                response.getServiceIndustryNames().get(0));
    }

    @Test
    void getTopRankIndustryNamesTest(){
        //given
        List<MSAnalyticsWithIndustryAndStoreNumDto> resultDto = Arrays.asList(
                MSAnalyticsWithIndustryAndStoreNumDto.builder()
                        .yearAndQuarterCode("20221").serviceIndustryCodeName("청과상").storesNumber(3).build(),
                MSAnalyticsWithIndustryAndStoreNumDto.builder()
                        .yearAndQuarterCode("20221").serviceIndustryCodeName("육류판매").storesNumber(2).build(),
                MSAnalyticsWithIndustryAndStoreNumDto.builder()
                        .yearAndQuarterCode("20222").serviceIndustryCodeName("핸드폰").storesNumber(1).build()
        );

        BDDMockito.given(marketSpaceAnalyticsService.getMSAnalyticsWithTopRankIndustryNames(Mockito.any(), Mockito.any()))
                .willReturn(resultDto);

        //when
        ServiceIndustryNamesResponse response =
                marketSpaceAnalyticsFacadeService.getTopRankIndustryNames(Arrays.asList("20221","20222"),
                        Arrays.asList("3110024","3110025"));

        //then
        Assertions.assertEquals(List.of("청과상","육류판매","핸드폰").get(0),
                response.getServiceIndustryNames().get(0));
    }

    @Test
    void getLowCloseRateStoreIndustryNamesTest(){
        //given
        List<MSAnalyticsWithIndustryAndCloseRateDto> resultDto = Arrays.asList(
                MSAnalyticsWithIndustryAndCloseRateDto.builder()
                        .yearAndQuarterCode("20221").serviceIndustryCodeName("청과상").bizCloseStoreRate(0).build(),
                MSAnalyticsWithIndustryAndCloseRateDto.builder()
                        .yearAndQuarterCode("20221").serviceIndustryCodeName("육류판매").bizCloseStoreRate(0).build(),
                MSAnalyticsWithIndustryAndCloseRateDto.builder()
                        .yearAndQuarterCode("20222").serviceIndustryCodeName("핸드폰").bizCloseStoreRate(0).build()
        );

        BDDMockito.given(marketSpaceAnalyticsService.getMSAnalyticsWithLowCloseRateIndustryNames(Mockito.any(), Mockito.any()))
                .willReturn(resultDto);

        //when
        List<ServiceIndustriesWithLowCloseRateResponse> response =
                marketSpaceAnalyticsFacadeService.getLowCloseRateStoreIndustryNames(Arrays.asList("20221","20222"),
                        Arrays.asList("3110024","3110025"));

        //then
        Assertions.assertEquals(List.of("청과상","육류판매","핸드폰").get(0),
                response.get(0).getServiceIndustryCodeName());
    }

}
