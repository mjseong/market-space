package com.juneox.marketspace.web.controller.impl;

import com.juneox.marketspace.web.controller.IMarketSpaceAnalyticsController;
import com.juneox.marketspace.web.facade.MarketSpaceAnalyticsFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/ms-analytics/v1")
@RestController
public class MarketSpaceAnalyticsController implements IMarketSpaceAnalyticsController {

    private final MarketSpaceAnalyticsFacadeService marketSpaceAnalyticsFacadeService;

    @GetMapping("/service-industries")
    public ResponseEntity getIndustryNames(@RequestParam(name = "year_quarter")List<String> yearAndQuarterCode){
        var responses = marketSpaceAnalyticsFacadeService.getIndustryNamesByYearQuarter(yearAndQuarterCode);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/service-industries/top-rank")
    public ResponseEntity getTopRankStores(@RequestParam(name = "year_quarter")List<String> yearAndQuarterCode,
                                           @RequestParam(name = "market_space_code")List<String> marketSpaceCode){
        var responses = marketSpaceAnalyticsFacadeService.getTopRankIndustryNames(yearAndQuarterCode, marketSpaceCode);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/service-industries/low-close-rate")
    public ResponseEntity getLowCloseRateStores(@RequestParam(name = "year_quarter")List<String> yearAndQuarterCode,
                                                @RequestParam(name = "market_space_code")List<String> marketSpaceCode){
        var responses = marketSpaceAnalyticsFacadeService.getLowCloseRateStoreIndustryNames(yearAndQuarterCode, marketSpaceCode);
        return ResponseEntity.ok().body(responses);
    }


}
