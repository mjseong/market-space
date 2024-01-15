package com.juneox.marketspace.test.service;

import com.juneox.marketspace.domain.meta.dto.MarketSpaceDto;
import com.juneox.marketspace.domain.meta.entity.MarketSpace;
import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.persistence.jpa.MarketSpaceRepository;
import com.juneox.marketspace.service.meta.MarketSpaceService;
import com.juneox.marketspace.service.meta.impl.MarketSpaceServiceImpl;
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
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MarketSpaceServiceTests {

    @Mock
    MarketSpaceRepository marketSpaceRepository;

    MarketSpaceService marketSpaceService;

    @BeforeEach
    void init(){
        this.marketSpaceService = new MarketSpaceServiceImpl(marketSpaceRepository);
    }

    @Test
    void createDataTest(){
        //given
        MarketSpaceDto dto = MarketSpaceDto.builder()
                .marketSpaceCode("")
                .marketSpaceCodeName("")
                .build();

        MarketSpace marketSpace = MarketSpace.builder()
                .id(1L)
                .marketSpaceCode("")
                .marketSpaceCodeName("")
                .build();

        BDDMockito.given(marketSpaceRepository.save(Mockito.any(MarketSpace.class))).willReturn(marketSpace);

        //when
        marketSpaceService.createMarketSpace(dto);

        //then
        Mockito.verify(marketSpaceRepository).save(Mockito.any(MarketSpace.class));
    }

    @Test
    void createBulkDataTest(){
        //given
        List<MarketSpaceDto> marketSpaceDtoList = Arrays.asList(
                MarketSpaceDto.builder()
                        .marketSpaceCode("3110023")
                        .marketSpaceCodeName("서울대병원")
                        .build(),
                MarketSpaceDto.builder()
                        .marketSpaceCode("3110024")
                        .marketSpaceCodeName("혜회동주민센터")
                        .build()
        );

        List<MarketSpace> marketSpaceList = Arrays.asList(
                MarketSpace.builder()
                        .id(1L)
                        .marketSpaceCode("3110023")
                        .marketSpaceCodeName("서울대병원")
                        .build(),
                MarketSpace.builder()
                        .id(2L)
                        .marketSpaceCode("3110024")
                        .marketSpaceCodeName("혜회동주민센터")
                        .build()
        );

        Map<String, MarketSpace> expectedMap = Map.of(
                "3110023", MarketSpace.builder()
                        .marketSpaceCode("3110023")
                        .marketSpaceCodeName("서울대병원")
                        .build(),
                "3110024", MarketSpace.builder()
                        .marketSpaceCode("3110024")
                        .marketSpaceCodeName("혜회동주민센터")
                        .build()
        );

        BDDMockito.given(marketSpaceRepository.saveAll(Mockito.any())).willReturn(marketSpaceList);

        //when
        Map<String, MarketSpace> actualMap = marketSpaceService.createBulkMarketSpace(marketSpaceDtoList);

        //then
        Assertions.assertEquals(expectedMap.get("3110023").getMarketSpaceCodeName(),
                actualMap.get("3110023").getMarketSpaceCodeName());

    }
}
