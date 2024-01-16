package com.juneox.marketspace.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=false)
@SuperBuilder
@Data
public class ServiceIndustriesWithLowCloseRateResponse extends ServiceIndustriesResponse{

    @JsonProperty("year_and_quarter")
    private String yearAndQuarter;
    @JsonProperty("market_space_code")
    private String marketSpaceCode;
    @JsonProperty("biz_close_store_rate")
    private int bizCloseStoreRate;

}
