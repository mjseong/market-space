package com.juneox.marketspace.domain.exception;

import com.juneox.marketspace.domain.exception.type.ErrorMessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class CommonException extends RuntimeException{

    @Getter
    private String code;
    @Getter
    private String message;
    @Getter
    private HttpStatus status;
    @Getter
    private ErrorMessageType type;

    public CommonException(ErrorMessageType errorMessageType) {
        super();
        this.type = errorMessageType;
        this.code = errorMessageType.getCode().toString();
        this.message = errorMessageType.getMessage();
        this.status = errorMessageType.getStatus();
    }

    public CommonException(ErrorMessageType errorMessageType, Throwable cause) {
        super(errorMessageType.getMessage(), cause);
        this.type = errorMessageType;
        this.code = errorMessageType.getCode().toString();
        this.message = errorMessageType.getMessage();
        this.status = errorMessageType.getStatus();
    }

}
