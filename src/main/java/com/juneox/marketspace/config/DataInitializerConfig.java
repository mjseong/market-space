package com.juneox.marketspace.config;

import com.juneox.marketspace.app.facade.MetaDataFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DataInitializerConfig {

    @Value("${app.market-space.file-store}")
    String fileStorePath;

    private final MetaDataFacadeService metaDataFacadeService;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData(){
        try{
            metaDataFacadeService.loadMarketSpaceDataFile(fileStorePath);
        }catch (RuntimeException re){
            log.error("load config init error: ", re);
        }

    }
}
