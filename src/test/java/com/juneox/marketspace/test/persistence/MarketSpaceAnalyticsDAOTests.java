package com.juneox.marketspace.test.persistence;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.domain.analysis.entity.MarketSpaceAnalytics;
import com.juneox.marketspace.domain.meta.entity.MarketSpace;
import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.domain.meta.entity.ServiceIndustry;
import com.juneox.marketspace.persistence.jdbc.MarketSpaceAnalyticsDAO;
import com.juneox.marketspace.persistence.jdbc.MysqlMarketSpaceAnalyticsDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MarketSpaceAnalyticsDAOTests {
    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    MarketSpaceAnalyticsDAO mysqlMarketSpaceAnalyticsDAO;

    @BeforeEach
    void init(){
        int batchSize = 1000;
        this.mysqlMarketSpaceAnalyticsDAO = new MysqlMarketSpaceAnalyticsDAO(jdbcTemplate, batchSize);
    }

    @Test
    void findAllByYearAndQuarterCodes(){
        //given
        List<String> yearAndQuarterCodes = Arrays.asList("20221","20222");
        List<MSAnalyticsWithIndustryDto> result = List.of(
                MSAnalyticsWithIndustryDto.builder()
                        .serviceIndustryCodeName("육류판매").build(),
                MSAnalyticsWithIndustryDto.builder()
                        .serviceIndustryCodeName("수산물판매").build(),
                MSAnalyticsWithIndustryDto.builder()
                        .serviceIndustryCodeName("스포츠 강습").build()
        );

        when(jdbcTemplate.query(anyString(), Mockito.any(Map.class), Mockito.any(RowMapper.class)))
                .thenReturn(result);

        List<MSAnalyticsWithIndustryDto> list = mysqlMarketSpaceAnalyticsDAO.findAllByYearAndQuarterCodes(yearAndQuarterCodes);

        Assertions.assertEquals(result.get(0).getServiceIndustryCodeName(), list.get(0).getServiceIndustryCodeName());
    }

    @Test
    void saveAll(){
        List<MarketSpaceAnalytics> entities = List.of(
                MarketSpaceAnalytics.builder()
                        .yearAndQuarterCode("20231")
                        .marketSpaceGroup(MarketSpaceGroup.builder().id(1L).build())
                        .marketSpace(MarketSpace.builder().id(1L).build())
                        .serviceIndustry(ServiceIndustry.builder().id(1L).build())
                        .storesNumber(1)
                        .similarIndustryStoreNumber(0)
                        .bizNewStoreRate(0)
                        .bizNewStoreNumber(0)
                        .bizCloseStoreRate(0)
                        .bizCloseStoreNumber(0)
                        .franchiseStoreNumber(0)
                        .build(),
                MarketSpaceAnalytics.builder()
                        .yearAndQuarterCode("20231")
                        .marketSpaceGroup(MarketSpaceGroup.builder().id(1L).build())
                        .marketSpace(MarketSpace.builder().id(1L).build())
                        .serviceIndustry(ServiceIndustry.builder().id(2L).build())
                        .storesNumber(1)
                        .similarIndustryStoreNumber(0)
                        .bizNewStoreRate(0)
                        .bizNewStoreNumber(0)
                        .bizCloseStoreRate(0)
                        .bizCloseStoreNumber(0)
                        .franchiseStoreNumber(0)
                        .build()
        );

        mysqlMarketSpaceAnalyticsDAO.saveAll(entities);
    }
}
