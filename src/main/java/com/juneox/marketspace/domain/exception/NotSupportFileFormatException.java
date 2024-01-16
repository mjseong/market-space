package com.juneox.marketspace.domain.exception;

public class NotSupportFileFormatException extends RuntimeException{

    public NotSupportFileFormatException(String message){
        super(message);
    }

    public NotSupportFileFormatException(Throwable throwable){
        super(throwable);
    }

    public NotSupportFileFormatException(String message, Throwable throwable){
        super(message, throwable);
    }
}
