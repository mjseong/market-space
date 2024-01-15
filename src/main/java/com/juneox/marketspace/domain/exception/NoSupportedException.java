package com.juneox.marketspace.domain.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoSupportedException extends RuntimeException{

    NoSupportedException(String message){
        super(message);
    }

}
