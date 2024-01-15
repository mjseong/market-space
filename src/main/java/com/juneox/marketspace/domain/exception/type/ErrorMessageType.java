package com.juneox.marketspace.domain.exception.type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = AccessLevel.PACKAGE)
public enum ErrorMessageType {
    MAINTENANCE(0, "Maintenance", HttpStatus.SERVICE_UNAVAILABLE),
    UNKNOWN(1, "Unknown", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER(10000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(10001, "Not found information", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(10002, "Unauthorized", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(10003,"Forbidden", HttpStatus.FORBIDDEN),
    NOT_IMPLEMENTED(10004, "Not Implemented feature", HttpStatus.NOT_IMPLEMENTED);

    @Getter
    Integer code;

    @Getter
    String message;

    @Getter
    HttpStatus status;

    ErrorMessageType(Integer code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
