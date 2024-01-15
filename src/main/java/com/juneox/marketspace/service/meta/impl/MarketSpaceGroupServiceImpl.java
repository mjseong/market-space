package com.juneox.marketspace.service.meta.impl;

import com.juneox.marketspace.domain.meta.dto.MarketSpaceGroupDto;
import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.domain.exception.NoSupportedException;
import com.juneox.marketspace.persistence.jpa.MarketSpaceGroupRepository;
import com.juneox.marketspace.service.meta.MarketSpaceGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketSpaceGroupServiceImpl implements MarketSpaceGroupService {

    private final MarketSpaceGroupRepository marketSpaceGroupRepository;

    @Override
    public MarketSpaceGroup getMarketSpaceGroup() {
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public MarketSpaceGroup createMarketSpaceGroup(MarketSpaceGroupDto marketSpaceGroupDto) {
        MarketSpaceGroup marketSpaceGroup = MarketSpaceGroup.builder()
                .marketSpaceGroupCode(marketSpaceGroupDto.getMarketSpaceGroupCode())
                .marketSpaceGroupCodeName(marketSpaceGroupDto.getMarketSpaceGroupCodeName())
                .build();

        return marketSpaceGroupRepository.save(marketSpaceGroup);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Map<String, MarketSpaceGroup> createBulkMarketSpaceGroup(List<MarketSpaceGroupDto> marketSpaceGroupDtos) {
        //bulk insert시에 중복 키 값 필터
        Map<String, MarketSpaceGroup> marketSpaceGroupMap = marketSpaceGroupRepository.findAll().stream()
                .collect(Collectors.toMap(p -> p.getMarketSpaceGroupCode(), p1 -> p1));

        List<MarketSpaceGroup> marketSpaceGroups = marketSpaceGroupDtos.stream()
                .filter(f -> !marketSpaceGroupMap.containsKey(f.getMarketSpaceGroupCode()))
                .map(marketSpaceGroupDto ->  MarketSpaceGroup.builder()
                        .marketSpaceGroupCode(marketSpaceGroupDto.getMarketSpaceGroupCode())
                        .marketSpaceGroupCodeName(marketSpaceGroupDto.getMarketSpaceGroupCodeName())
                        .build())
                .collect(Collectors.toList());

        marketSpaceGroupMap.putAll(
                marketSpaceGroupRepository.saveAll(marketSpaceGroups).stream()
                        .collect(Collectors.toMap(p -> p.getMarketSpaceGroupCode(), p1 -> p1))
        );

        return marketSpaceGroupMap;
    }

    @Override
    public MarketSpaceGroup modifyMarketSpaceGroup() {
        throw new NoSupportedException();
    }

    @Override
    public void deleteMarketSpaceGroup(long id) {
        throw new NoSupportedException();
    }
}
