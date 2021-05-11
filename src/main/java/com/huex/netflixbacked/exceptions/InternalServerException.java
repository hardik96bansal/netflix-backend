package com.huex.netflixbacked.exceptions;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String message){
        super(message);
    }
}
