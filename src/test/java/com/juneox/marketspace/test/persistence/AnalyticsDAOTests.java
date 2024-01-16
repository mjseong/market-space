package com.juneox.marketspace.test.persistence;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.persistence.jdbc.AnalyticsDAO;
import com.juneox.marketspace.persistence.jdbc.MysqlAnalyticsDAO;
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
public class AnalyticsDAOTests {
    @Mock
    NamedParameterJdbcTemplate jdbcTemplate;

    AnalyticsDAO mysqlAnalyticsDAO;

    @BeforeEach
    void init(){
        this.mysqlAnalyticsDAO = new MysqlAnalyticsDAO(jdbcTemplate);
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

        List<MSAnalyticsWithIndustryDto> list = mysqlAnalyticsDAO.findAllByYearAndQuarterCodes(yearAndQuarterCodes);

        Assertions.assertEquals(result.get(0).getServiceIndustryCodeName(), list.get(0).getServiceIndustryCodeName());
    }
}
