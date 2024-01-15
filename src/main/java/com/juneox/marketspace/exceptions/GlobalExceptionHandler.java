package com.juneox.marketspace.exceptions;

import com.juneox.marketspace.app.response.ErrorResponse;
import com.juneox.marketspace.domain.exception.CommonException;
import com.juneox.marketspace.domain.exception.NoSupportedException;
import com.juneox.marketspace.domain.exception.type.ErrorMessageType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity handleCommonException(CommonException ce){
        return ResponseEntity.status(ce.getStatus()).body(ErrorResponse.from(ce.getType()));
    }

    @ExceptionHandler(NoSupportedException.class)
    public ResponseEntity handleNotSupportedException(NoSupportedException nse){
        return ResponseEntity.status(ErrorMessageType.NOT_IMPLEMENTED.getStatus())
                .body(ErrorResponse.from(ErrorMessageType.NOT_IMPLEMENTED));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleNotSupportedException(RuntimeException re){
        return ResponseEntity.status(ErrorMessageType.INTERNAL_SERVER.getStatus())
                .body(ErrorResponse.from(ErrorMessageType.INTERNAL_SERVER));
    }

}
