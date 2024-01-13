package com.juneox.marketspace.config;

import com.juneox.marketspace.web.facade.MetaDataFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DataInitializerConfig {

    private final MetaDataFacadeService metaDataFacadeService;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData(){
        try{
            metaDataFacadeService.loadMarketSpaceDataFile("./sample/sample.csv");
        }catch (RuntimeException re){
            log.error("load config init error: ", re);
        }

    }
}
