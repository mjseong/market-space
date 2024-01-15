package com.juneox.marketspace.test.service;

import com.juneox.marketspace.persistence.jdbc.AnalyticsJdbcRepository;
import com.juneox.marketspace.persistence.jpa.MarketSpaceAnalyticsRepository;
import com.juneox.marketspace.persistence.qdsl.AnalyticsQDSLRepository;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import com.juneox.marketspace.service.analysis.impl.MarketSpaceAnalyticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MarkSpaceAnalyticsServiceTests {

    @Mock
    MarketSpaceAnalyticsRepository marketSpaceAnalyticsRepository;

    @Mock
    AnalyticsJdbcRepository analyticsJdbcRepository;

    @Mock
    AnalyticsQDSLRepository analyticsQDSLRepository;

    MarketSpaceAnalyticsService marketSpaceAnalyticsService;

    @BeforeEach
    void init(){
        this.marketSpaceAnalyticsService =
                new MarketSpaceAnalyticsServiceImpl(
                        marketSpaceAnalyticsRepository, analyticsQDSLRepository, analyticsJdbcRepository);
    }

    @Test
    void createDataTest(){

    }
}
