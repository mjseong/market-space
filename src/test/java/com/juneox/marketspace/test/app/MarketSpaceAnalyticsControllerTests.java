package com.juneox.marketspace.test.app;

import com.juneox.marketspace.app.controller.impl.MarketSpaceAnalyticsControllerImpl;
import com.juneox.marketspace.app.facade.MarketSpaceAnalyticsFacadeService;
import com.juneox.marketspace.app.response.ServiceIndustryNamesResponse;
import com.juneox.marketspace.domain.exception.CommonException;
import com.juneox.marketspace.domain.exception.type.ErrorMessageType;
import com.juneox.marketspace.domain.meta.cache.MetaCacheStore;
import com.juneox.marketspace.domain.meta.entity.MarketSpace;
import com.juneox.marketspace.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MarketSpaceAnalyticsControllerTests {

    @InjectMocks
    MarketSpaceAnalyticsControllerImpl marketSpaceAnalyticsController;
    @Mock
    MarketSpaceAnalyticsFacadeService marketSpaceAnalyticsFacadeService;

    private MockMvc mockMvc;

    @BeforeEach
    void init(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(marketSpaceAnalyticsController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        MetaCacheStore.getInstance().putAllYearAndQuarterCode(Set.of("20221","20222","20223","20231"));
        MetaCacheStore.getInstance().putAllMarketSpace(
                List.of(
                        MarketSpace.builder().marketSpaceCode("C30001").build(),
                        MarketSpace.builder().marketSpaceCode("C30002").build(),
                        MarketSpace.builder().marketSpaceCode("C30003").build()
                )
        );
    }

    @Test
    void getIndustryNames200Test() throws Exception {
        //given
        ServiceIndustryNamesResponse response = ServiceIndustryNamesResponse.builder().build();
        Mockito.doReturn(response).when(marketSpaceAnalyticsFacadeService).getIndustryNamesByYearQuarter(Mockito.any(List.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.get("/ms-analytics/v1/service-industries")
                                .queryParam("year_quarter","20221")
                                .queryParam("year_quarter","20223")
                )
                .andDo(print());

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void getIndustryNames400Test() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/ms-analytics/v1/service-industries")
                        .queryParam("year_quarter","20242"));

        //then
        resultActions.andExpect(status().is(400)).andReturn();
    }

    @Test
    void getIndustryNames404Test() throws Exception {
        //given
        ServiceIndustryNamesResponse response = ServiceIndustryNamesResponse.builder().build();
        Mockito.when(marketSpaceAnalyticsFacadeService.getIndustryNamesByYearQuarter(Mockito.any(List.class)))
                .thenThrow(new CommonException(ErrorMessageType.NOT_FOUND));

        //when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.get("/ms-analytics/v1/service-industries")
                                .queryParam("year_quarter","20221")
                                .queryParam("year_quarter","20223")
                )
                .andDo(print());

        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    void getIndustryNames500Test() throws Exception {
        //given
        ServiceIndustryNamesResponse response = ServiceIndustryNamesResponse.builder().build();
        Mockito.when(marketSpaceAnalyticsFacadeService.getIndustryNamesByYearQuarter(Mockito.any(List.class)))
                .thenThrow(new CommonException(ErrorMessageType.INTERNAL_SERVER));

        //when
        ResultActions resultActions = mockMvc.perform(
                        MockMvcRequestBuilders.get("/ms-analytics/v1/service-industries")
                                .queryParam("year_quarter","20221")
                                .queryParam("year_quarter","20223")
                )
                .andDo(print());

        //then
        resultActions.andExpect(status().is5xxServerError());
    }



    @Test
    void getTopRankStoresTest() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/ms-analytics/v1/service-industries/top-rank")
                        .queryParam("year_quarter","20232")
                        .queryParam("market_space_code","C30001")
                        .queryParam("year_quarter","20231")
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    void getTopRankStores400Test() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/ms-analytics/v1/service-industries/top-rank")
                        .queryParam("year_quarter","20232")
                        .queryParam("market_space_code","C30004")
                        .queryParam("year_quarter","20231")
        );

        //then
        resultActions.andExpect(status().is(400)).andReturn();
    }

    @Test
    void getLowCloseRateStores() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/ms-analytics/v1/service-industries/low-close-rate")
                        .queryParam("year_quarter","20232")
                        .queryParam("market_space_code","C30001")
                        .queryParam("year_quarter","20231")
        ).andDo(print());

        //then
        resultActions.andExpect(status().isOk());
    }

}
