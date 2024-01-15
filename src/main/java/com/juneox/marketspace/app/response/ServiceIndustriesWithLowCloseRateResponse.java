package com.juneox.marketspace.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class ServiceIndustriesWithLowCloseRateResponse extends ServiceIndustriesResponse{

    @JsonProperty("biz_close_store_rate")
    private int bizCloseStoreRate;

}
