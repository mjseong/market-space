package com.juneox.marketspace.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.juneox.marketspace.domain.exception.dto.AppError;
import com.juneox.marketspace.domain.exception.type.ErrorMessageType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

    ErrorResponse(AppError error){
        this.error = error;
    }

    @JsonProperty("error")
    AppError error;

    public static ErrorResponse from(ErrorMessageType errorMessageType){
        AppError error = AppError.builder()
                .code(errorMessageType.getCode())
                .message(errorMessageType.getMessage())
                .build();


        return new ErrorResponse(error);
    }

    public static ErrorResponse from(int code, String message){
        AppError error = AppError.builder()
                .code(code)
                .message(message)
                .build();

        return new ErrorResponse(error);
    }


}
