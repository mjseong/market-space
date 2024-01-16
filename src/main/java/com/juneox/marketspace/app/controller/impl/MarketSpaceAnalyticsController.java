package com.juneox.marketspace.app.controller.impl;

import com.juneox.marketspace.app.controller.IMarketSpaceAnalyticsController;
import com.juneox.marketspace.app.facade.MarketSpaceAnalyticsFacadeService;
import com.juneox.marketspace.domain.exception.CommonException;
import com.juneox.marketspace.domain.exception.type.ErrorMessageType;
import com.juneox.marketspace.domain.meta.cache.MetaCacheStore;
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
        //validation check
        this.validationYearAndQuarterCode(yearAndQuarterCode);

        var responses = marketSpaceAnalyticsFacadeService.getIndustryNamesByYearQuarter(yearAndQuarterCode);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/service-industries/top-rank")
    public ResponseEntity getTopRankStores(@RequestParam(name = "year_quarter")List<String> yearAndQuarterCode,
                                           @RequestParam(name = "market_space_code")List<String> marketSpaceCode){
        //validation check
        this.validationYearAndQuarterCode(yearAndQuarterCode);
        this.validationMarketSpaceCode(marketSpaceCode);

        var responses = marketSpaceAnalyticsFacadeService.getTopRankIndustryNames(yearAndQuarterCode, marketSpaceCode);
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/service-industries/low-close-rate")
    public ResponseEntity getLowCloseRateStores(@RequestParam(name = "year_quarter")List<String> yearAndQuarterCode,
                                                @RequestParam(name = "market_space_code")List<String> marketSpaceCode){
        //validation check
        this.validationYearAndQuarterCode(yearAndQuarterCode);
        this.validationMarketSpaceCode(marketSpaceCode);

        var responses = marketSpaceAnalyticsFacadeService.getLowCloseRateStoreIndustryNames(yearAndQuarterCode, marketSpaceCode);
        return ResponseEntity.ok().body(responses);
    }

    private void validationYearAndQuarterCode(List<String> yearAndQuarterCodes){
        yearAndQuarterCodes.stream()
                .filter(f->MetaCacheStore.getInstance().existYearAndQuarter(f))
                .findFirst().orElseThrow(()->new CommonException(ErrorMessageType.INVALID_REQUEST));
    }

    private void validationMarketSpaceCode(List<String> marketSpaceCode){
        marketSpaceCode.stream()
                .filter(f->MetaCacheStore.getInstance().existMarketSpace(f))
                .findFirst().orElseThrow(() -> new CommonException(ErrorMessageType.INVALID_REQUEST));
    }

}
