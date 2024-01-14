package com.juneox.marketspace.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IMarketSpaceAnalyticsController {

    public ResponseEntity getIndustryNames(@RequestParam(name = "year_quarter") List<String> yearAndQuarterCode);
}
