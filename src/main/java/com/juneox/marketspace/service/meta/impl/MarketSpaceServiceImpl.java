package com.juneox.marketspace.service.meta.impl;

import com.juneox.marketspace.domain.model.meta.dto.MarketSpaceDto;
import com.juneox.marketspace.domain.model.meta.entity.MarketSpace;
import com.juneox.marketspace.persistence.jpa.MarketSpaceRepository;
import com.juneox.marketspace.service.meta.MarketSpaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketSpaceServiceImpl implements MarketSpaceService {

    private final MarketSpaceRepository marketSpaceRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public MarketSpace createMarketSpace(MarketSpaceDto marketSpaceDto) {

        MarketSpace marketSpace = MarketSpace.builder()
                .marketSpaceCode(marketSpaceDto.getMarketSpaceCode())
                .marketSpaceCodeName(marketSpaceDto.getMarketSpaceCodeName())
                .build();

        return marketSpaceRepository.save(marketSpace);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Map<String, MarketSpace> createBulkMarketSpace(List<MarketSpaceDto> marketSpaceDtos) {
        //bulk insert시에 중복 키 값 필터
        Map<String, MarketSpace> marketSpaceMap = marketSpaceRepository.findAll().stream()
                .collect(Collectors.toMap(p -> p.getMarketSpaceCode(), p1 -> p1));

        List<MarketSpace> marketSpaces = marketSpaceDtos.stream()
                .filter(f -> !marketSpaceMap.containsKey(f.getMarketSpaceCode()))
                .map(marketSpaceDto -> MarketSpace.builder()
                        .marketSpaceCode(marketSpaceDto.getMarketSpaceCode())
                        .marketSpaceCodeName(marketSpaceDto.getMarketSpaceCodeName())
                        .build())
                .collect(Collectors.toList());

        marketSpaceMap.putAll(
                marketSpaceRepository.saveAll(marketSpaces).stream()
                        .collect(Collectors.toMap(p -> p.getMarketSpaceCode(), p1->p1))
        );

        return marketSpaceMap;
    }

    @Transactional
    @Override
    public MarketSpace modifyMarketSpace() {
        return null;
    }

    @Transactional
    @Override
    public void deleteMarketSpace(long id) {

    }
}
