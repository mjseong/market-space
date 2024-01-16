package com.juneox.marketspace.domain.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataLoadFailureException extends RuntimeException{
    public DataLoadFailureException(String message){
        super(message);
    }

    public DataLoadFailureException(Throwable throwable){
        super(throwable);
    }

    public DataLoadFailureException(String message, Throwable throwable){
        super(message, throwable);
    }
}
