package com.juneox.marketspace.test.app;

import com.juneox.marketspace.app.facade.MetaDataFacadeService;
import com.juneox.marketspace.domain.exception.DataLoadFailureException;
import com.juneox.marketspace.domain.exception.NotSupportFileFormatException;
import com.juneox.marketspace.domain.meta.cache.MetaCacheStore;
import com.juneox.marketspace.domain.meta.dto.MarketSpaceDto;
import com.juneox.marketspace.domain.meta.dto.MarketSpaceGroupDto;
import com.juneox.marketspace.domain.meta.dto.ServiceIndustryDto;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import com.juneox.marketspace.service.meta.MarketSpaceGroupService;
import com.juneox.marketspace.service.meta.MarketSpaceService;
import com.juneox.marketspace.service.meta.ServiceIndustryService;
import com.juneox.marketspace.service.raw.FileMetaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MetaDataFacadeServiceTests {

    @Autowired
    MetaDataFacadeService metaDataFacadeService;

    @MockBean
    MarketSpaceGroupService marketSpaceGroupService;

    @MockBean
    MarketSpaceService marketSpaceService;

    @MockBean
    ServiceIndustryService serviceIndustryService;

    @MockBean
    MarketSpaceAnalyticsService marketSpaceAnalyticsService;

    @MockBean
    FileMetaService fileMetaService;

    @Test
    void sampleDataMetaCacheTest(){
        //given
        Map msGroupMap = Map.of();
        Map msMap = Map.of();
        Map siMap = Map.of();

        BDDMockito.given(fileMetaService.getFileMetas()).willReturn(Arrays.asList());
        BDDMockito.given(marketSpaceGroupService.createBulkMarketSpaceGroup(Mockito.any())).willReturn(msGroupMap);
        BDDMockito.given(marketSpaceService.createBulkMarketSpace(Mockito.any())).willReturn(msMap);
        BDDMockito.given(serviceIndustryService.createBulkServiceIndustry(Mockito.any())).willReturn(siMap);

        //when
        metaDataFacadeService.loadMarketSpaceDataFile("./sample");
        List<MarketSpaceGroupDto> msGroupList = new ArrayList<>(MetaCacheStore.getInstance().getMarketSpaceGroupDtoMap().values());
        List<MarketSpaceDto> msList = new ArrayList<>(MetaCacheStore.getInstance().getMarketSpaceDtoMap().values());
        List<ServiceIndustryDto> siList = new ArrayList<>(MetaCacheStore.getInstance().getServiceIndustryDtoMap().values());

        //then
        Assertions.assertEquals(3, msGroupList.size());
        Assertions.assertEquals(45, msList.size());
        Assertions.assertEquals(43, siList.size());
    }

    @Test
    void loadDataTest(){
        //given
        BDDMockito.given(fileMetaService.getFileMetas()).willReturn(Arrays.asList());
        long expectedCount =
                metaDataFacadeService.parseMarketSpaceCsv("./sample/sample2021limit60.csv").size();

        //when
        long actualCount = metaDataFacadeService.loadMarketSpaceDataFile("./sample");

        //then
        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    void loadDirFailTest(){
        Assertions.assertThrows(DataLoadFailureException.class, ()->{
            metaDataFacadeService.loadMarketSpaceDataFile("./sample1");
        });
    }

    @Test
    void loadCsvFailTest(){
        Assertions.assertThrows(NotSupportFileFormatException.class, ()->{
            metaDataFacadeService.parseMarketSpaceCsv("./sample_fail/samplefail.csv");
        });
    }

    @Test
    void loadCsvNotFileTest(){
        Assertions.assertThrows(NotSupportFileFormatException.class, ()->{
            metaDataFacadeService.parseMarketSpaceCsv("./sample_fail/sample.txt");
        });
    }
}
