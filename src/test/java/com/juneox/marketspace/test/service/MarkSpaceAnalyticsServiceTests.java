package com.juneox.marketspace.test.service;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.domain.analysis.dto.MarketSpaceAnalyticsDto;
import com.juneox.marketspace.domain.analysis.entity.MarketSpaceAnalytics;
import com.juneox.marketspace.domain.meta.entity.MarketSpace;
import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.domain.meta.entity.ServiceIndustry;
import com.juneox.marketspace.persistence.jdbc.MarketSpaceAnalyticsDAO;
import com.juneox.marketspace.persistence.jpa.MarketSpaceAnalyticsRepository;
import com.juneox.marketspace.persistence.qdsl.MarketSpaceAnalyticsQDSLRepository;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import com.juneox.marketspace.service.analysis.impl.MarketSpaceAnalyticsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MarkSpaceAnalyticsServiceTests {

    @Mock
    MarketSpaceAnalyticsRepository marketSpaceAnalyticsRepository;

    @Mock
    MarketSpaceAnalyticsDAO marketSpaceAnalyticsDAO;

    @Mock
    MarketSpaceAnalyticsQDSLRepository marketSpaceAnalyticsQDSLRepository;

    MarketSpaceAnalyticsService marketSpaceAnalyticsService;

    @BeforeEach
    void init(){
        this.marketSpaceAnalyticsService =
                new MarketSpaceAnalyticsServiceImpl(
                        marketSpaceAnalyticsRepository, marketSpaceAnalyticsQDSLRepository, marketSpaceAnalyticsDAO);
    }

    @Test
    void getMSAnalyticsWithIndustryNamesTest(){
        //given
        List<String> yearQuarter = Arrays.asList("20221","20222");

        List<MSAnalyticsWithIndustryDto> expectedList = Arrays.asList(
                MSAnalyticsWithIndustryDto.builder()
                        .serviceIndustryCodeName("육류판매")
                        .build(),
                MSAnalyticsWithIndustryDto.builder()
                        .serviceIndustryCodeName("수산물판매")
                        .build()
        );

        //when
        Mockito.when(marketSpaceAnalyticsDAO.findAllByYearAndQuarterCodes(Mockito.any())).thenReturn(expectedList);
        List<MSAnalyticsWithIndustryDto> actualList =
                marketSpaceAnalyticsService.getMSAnalyticsWithIndustryNames(yearQuarter);

        //then
        Assertions.assertEquals(expectedList.get(0).getServiceIndustryCodeName(),
                actualList.get(0).getServiceIndustryCodeName());

    }

    @Test
    void getMSAnalyticsWithTopRankIndustryNamesTest(){
        //given
        List<String> yearQuarter = Arrays.asList("20221","20222");
        List<String> msCodes = Arrays.asList("3110024","3110025");

        List<MSAnalyticsWithIndustryAndStoreNumDto> expectedList = Arrays.asList(
                MSAnalyticsWithIndustryAndStoreNumDto.builder()
                        .yearAndQuarterCode("20221")
                        .serviceIndustryCodeName("육류판매")
                        .storesNumber(6)
                        .build(),
                MSAnalyticsWithIndustryAndStoreNumDto.builder()
                        .yearAndQuarterCode("20221")
                        .serviceIndustryCodeName("수산물판매")
                        .storesNumber(5)
                        .build(),
                MSAnalyticsWithIndustryAndStoreNumDto.builder()
                        .yearAndQuarterCode("20221")
                        .serviceIndustryCodeName("스포츠 강습")
                        .storesNumber(2)
                        .build(),
                MSAnalyticsWithIndustryAndStoreNumDto.builder()
                        .yearAndQuarterCode("20222")
                        .serviceIndustryCodeName("컴퓨터학원")
                        .storesNumber(1)
                        .build(),
                MSAnalyticsWithIndustryAndStoreNumDto.builder()
                        .yearAndQuarterCode("20222")
                        .serviceIndustryCodeName("동물병원")
                        .storesNumber(1)
                        .build()
        );

        //when
        Mockito.when(marketSpaceAnalyticsQDSLRepository.findAllTopRankByYearQuarterCodesAndMsCode(Mockito.any(), Mockito.any()))
                .thenReturn(expectedList);
        List<MSAnalyticsWithIndustryAndStoreNumDto> actualList =
                marketSpaceAnalyticsService.getMSAnalyticsWithTopRankIndustryNames(yearQuarter, msCodes);

        //then
        Assertions.assertEquals(expectedList.get(0).getServiceIndustryCodeName(),
                actualList.get(0).getServiceIndustryCodeName());
    }

    @Test
    void getMSAnalyticsWithLowCloseRateIndustryNamesTest(){
        //given
        List<String> yearQuarter = Arrays.asList("20221","20222");
        List<String> msCodes = Arrays.asList("3110024","3110025");

        List<MSAnalyticsWithIndustryAndCloseRateDto> expectedList = Arrays.asList(
                MSAnalyticsWithIndustryAndCloseRateDto.builder()
                        .build(),
                MSAnalyticsWithIndustryAndCloseRateDto.builder()
                        .build(),
                MSAnalyticsWithIndustryAndCloseRateDto.builder()
                        .build(),
                MSAnalyticsWithIndustryAndCloseRateDto.builder()
                        .build(),
                MSAnalyticsWithIndustryAndCloseRateDto.builder()
                        .build()
        );

        //when
        Mockito.when(marketSpaceAnalyticsDAO.findAllCloseRateByYearQuarterCodesAndMsCode(Mockito.any(), Mockito.any()))
                .thenReturn(expectedList);
        List<MSAnalyticsWithIndustryAndCloseRateDto> actualList =
                marketSpaceAnalyticsService.getMSAnalyticsWithLowCloseRateIndustryNames(yearQuarter, msCodes);

        //then
        Assertions.assertEquals(5,
                actualList.size());
    }
}
