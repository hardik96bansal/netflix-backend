package com.huex.netflixbacked.exceptions;

public class UnautorizedAccessException extends RuntimeException {
     public UnautorizedAccessException(String message){
         super(message);
     }
}
