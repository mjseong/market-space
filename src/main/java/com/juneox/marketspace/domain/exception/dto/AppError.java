package com.juneox.marketspace.domain.exception.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppError {

    @JsonProperty("code")
    int code;
    @JsonProperty("message")
    String message;
}
