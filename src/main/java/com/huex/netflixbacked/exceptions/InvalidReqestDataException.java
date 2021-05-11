package com.huex.netflixbacked.exceptions;

public class InvalidReqestDataException extends RuntimeException{
    public InvalidReqestDataException(String message){
        super(message);
    }
}
