package com.juneox.marketspace.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class ServiceIndustriesWthStoreNumResponse extends ServiceIndustriesResponse {

    @JsonProperty("stores_number")
    private int storesNumber;
}
