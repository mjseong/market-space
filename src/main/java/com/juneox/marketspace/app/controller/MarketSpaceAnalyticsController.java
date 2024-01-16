package com.juneox.marketspace.app.controller;

import com.juneox.marketspace.app.response.ErrorResponse;
import com.juneox.marketspace.app.response.ServiceIndustryNamesResponse;
import com.juneox.marketspace.common.constant.ErrorConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MarketSpaceAnalyticsController {

    @Operation(summary = "getIndustryNames API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(schema = @Schema(implementation = ServiceIndustryNamesResponse.class))}),
            @ApiResponse(responseCode = "400", description = "400 client error",
                    content = {@Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = ErrorConstant.INVALID_REQUEST, value =
                                            """
                                                {
                                                  "error": {
                                                    "code": 10051,
                                                    "message": "Invalid request"
                                                  }
                                                }
                                            """
                                    )
                            }
                    )}
            ),
            @ApiResponse(responseCode = "500", description = "500 server error",
                    content = {@Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = ErrorConstant.INTERNAL_SERVER, value =
                                            """
                                                {
                                                  "error": {
                                                    "code": 10000,
                                                    "message": "Internal server error"
                                                  }
                                                }
                                            """
                                    )}
                    )}),
    })
    public ResponseEntity getIndustryNames(@RequestParam(name = "year_quarter")List<String> yearAndQuarterCode);

    @Operation(summary = "getTop5RankStores API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(schema = @Schema(implementation = ServiceIndustryNamesResponse.class))}),
            @ApiResponse(responseCode = "400", description = "400 client error",
                    content = {@Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = ErrorConstant.INVALID_REQUEST, value =
                                            """
                                                {
                                                  "error": {
                                                    "code": 10051,
                                                    "message": "Invalid request"
                                                  }
                                                }
                                            """
                                    )
                            }
                    )}
            ),
            @ApiResponse(responseCode = "500", description = "500 server error",
                    content = {@Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = ErrorConstant.INTERNAL_SERVER, value =
                                            """
                                                {
                                                  "error": {
                                                    "code": 10000,
                                                    "message": "Internal server error"
                                                  }
                                                }
                                            """
                                    )}
                    )}),
    })
    public ResponseEntity getTopRankStores(@RequestParam(name = "year_quarter")List<String> yearAndQuarterCode,
                                           @RequestParam(name = "market_space_code")List<String> marketSpaceCode);

    @Operation(summary = "getLowCloseRateStores API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(schema = @Schema(implementation = ServiceIndustryNamesResponse.class))}),
            @ApiResponse(responseCode = "400", description = "400 client error",
                    content = {@Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = ErrorConstant.INVALID_REQUEST, value =
                                            """
                                                {
                                                  "error": {
                                                    "code": 10051,
                                                    "message": "Invalid request"
                                                  }
                                                }
                                            """
                                    )
                            }
                    )}
            ),
            @ApiResponse(responseCode = "500", description = "500 server error",
                    content = {@Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = ErrorConstant.INTERNAL_SERVER, value =
                                            """
                                                {
                                                  "error": {
                                                    "code": 10000,
                                                    "message": "Internal server error"
                                                  }
                                                }
                                            """
                                    )}
                    )}),
    })
    public ResponseEntity getLowCloseRateStores(@RequestParam(name = "year_quarter")List<String> yearAndQuarterCode,
                                                @RequestParam(name = "market_space_code")List<String> marketSpaceCode);
}
