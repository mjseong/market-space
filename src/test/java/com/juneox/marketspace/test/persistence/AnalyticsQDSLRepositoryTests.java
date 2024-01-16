package com.juneox.marketspace.test.persistence;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.analysis.entity.QMarketSpaceAnalytics;
import com.juneox.marketspace.domain.exception.NoSupportedException;
import com.juneox.marketspace.persistence.qdsl.AnalyticsQDSLRepository;
import com.juneox.marketspace.persistence.qdsl.AnalyticsQDSLRepositoryImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AnalyticsQDSLRepositoryTests {

    @Mock
    JPAQueryFactory jpaQueryFactory;
    AnalyticsQDSLRepository analyticsQDSLRepository;

    @BeforeEach
    void init(){
        this.analyticsQDSLRepository = new AnalyticsQDSLRepositoryImpl(jpaQueryFactory);
    }

    @Test
    void findAllTopRankByYearQuarterCodesAndMsCodeTest(){
        //given
        List<String> yearAndQuarterCodes = Arrays.asList("20221","20222");
        List<String> marketSpaceCodes = Arrays.asList("3110024","3110025");

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

        QMarketSpaceAnalytics msa = QMarketSpaceAnalytics.marketSpaceAnalytics;

        JPAQuery step1 = mock(JPAQuery.class);
        JPAQuery step2 = mock(JPAQuery.class);
        JPAQuery step3 = mock(JPAQuery.class);
        JPAQuery step4 = mock(JPAQuery.class);
        JPAQuery step5 = mock(JPAQuery.class);

        BDDMockito.given(jpaQueryFactory.select(
                Projections.constructor(MSAnalyticsWithIndustryAndStoreNumDto.class,
                msa.serviceIndustry.serviceIndustryCodeName,
                msa.yearAndQuarterCode,
                msa.storesNumber))).willReturn(step1);

        BDDMockito.given(step1.from(msa)).willReturn(step2);

        BDDMockito.given(step2.where(msa.yearAndQuarterCode.in(yearAndQuarterCodes)
                .and(msa.marketSpace.marketSpaceCode.in(marketSpaceCodes)))).willReturn(step3);

        BDDMockito.given(step3.orderBy(msa.storesNumber.desc())).willReturn(step4);

        BDDMockito.given(step4.limit(5)).willReturn(step5);

        BDDMockito.given(step5.fetch()).willReturn(expectedList);

        //when
        List<MSAnalyticsWithIndustryAndStoreNumDto> actualList =
        analyticsQDSLRepository.findAllTopRankByYearQuarterCodesAndMsCode(yearAndQuarterCodes, marketSpaceCodes);

        Assertions.assertEquals(expectedList.size(), actualList.size());
    }

    @Test
    void findAllCloseRateByYearQuarterCodesAndMsCodeTest(){
        //given
        List<String> yearAndQuarterCodes = Arrays.asList("20221","20222");
        List<String> marketSpaceCodes = Arrays.asList("3110024","3110025");

        Assertions.assertThrows(NoSupportedException.class, ()->{
            analyticsQDSLRepository.findAllCloseRateByYearQuarterCodesAndMsCode(yearAndQuarterCodes, marketSpaceCodes);
        });
    }




}
