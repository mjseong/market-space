package com.juneox.marketspace.test.service;

import com.juneox.marketspace.domain.meta.dto.MarketSpaceGroupDto;
import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.persistence.jpa.MarketSpaceGroupRepository;
import com.juneox.marketspace.service.meta.MarketSpaceGroupService;
import com.juneox.marketspace.service.meta.impl.MarketSpaceGroupServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MarketSpaceGroupServiceTests {

    @Mock
    MarketSpaceGroupRepository marketSpaceGroupRepository;

    MarketSpaceGroupService marketSpaceGroupService;

    @BeforeEach
    void init(){
        this.marketSpaceGroupService = new MarketSpaceGroupServiceImpl(marketSpaceGroupRepository);
    }

    @Test
    void createTest(){

        //given
        MarketSpaceGroupDto marketSpaceGroupDto = MarketSpaceGroupDto.builder()
                .marketSpaceGroupCode("U")
                .marketSpaceGroupCodeName("골목상권")
                .build();

        var expectedGroup = MarketSpaceGroup.builder()
                .id(1L)
                .marketSpaceGroupCode("U")
                .marketSpaceGroupCodeName("골목상권")
                .build();

        given(marketSpaceGroupRepository.save(Mockito.any(MarketSpaceGroup.class))).willReturn(expectedGroup);

        marketSpaceGroupService.createMarketSpaceGroup(marketSpaceGroupDto);

        //then
        verify(marketSpaceGroupRepository).save(Mockito.any(MarketSpaceGroup.class));

    }

    @Test
    void createBulkDataTest(){

        //given
        List<MarketSpaceGroupDto> marketSpaceGroupDtos = Arrays.asList(
                MarketSpaceGroupDto.builder()
                        .marketSpaceGroupCode("A")
                        .marketSpaceGroupCodeName("골목상권")
                        .build(),
                MarketSpaceGroupDto.builder()
                        .marketSpaceGroupCode("R")
                        .marketSpaceGroupCodeName("전통시장")
                        .build()
        );
        List<MarketSpaceGroup> willList = Arrays.asList(
                MarketSpaceGroup.builder()
                        .id(1L)
                        .marketSpaceGroupCode("A")
                        .marketSpaceGroupCodeName("골목상권")
                        .build(),
                MarketSpaceGroup.builder()
                        .id(2L)
                        .marketSpaceGroupCode("R")
                        .marketSpaceGroupCodeName("전통시장")
                        .build()
        );

        Map<String, MarketSpaceGroup> expectdMap =
                Map.of("A",MarketSpaceGroup.builder()
                                .id(1L)
                                .marketSpaceGroupCode("A")
                                .marketSpaceGroupCodeName("골목상권")
                        .build(),
                "R",MarketSpaceGroup.builder()
                                .id(2L)
                                .marketSpaceGroupCode("R")
                                .marketSpaceGroupCodeName("전통시장")
                        .build());

        given(marketSpaceGroupRepository.saveAll(Mockito.any(List.class)))
                .willReturn(willList);

        //when
        Map<String, MarketSpaceGroup> actualMap = marketSpaceGroupService.createBulkMarketSpaceGroup(marketSpaceGroupDtos);

        //then
        Assertions.assertEquals(expectdMap.get("A").getMarketSpaceGroupCodeName(),
                actualMap.get("A").getMarketSpaceGroupCodeName());
    }


}
