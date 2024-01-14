package com.juneox.marketspace.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class OpenApiDocConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().addServersItem(new Server().url("/"));
    }

    @Bean
    public GroupedOpenApi marketSpaceAnalyticsApi(){
        return GroupedOpenApi.builder()
                .group("market-space")
                .pathsToMatch("/ms-analytics/v1/service-industries/**")
                .build();
    }
}
