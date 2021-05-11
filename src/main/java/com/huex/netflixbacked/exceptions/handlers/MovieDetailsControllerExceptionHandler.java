package com.huex.netflixbacked.exceptions.handlers;

import com.huex.netflixbacked.exceptions.InternalServerException;
import com.huex.netflixbacked.exceptions.InvalidReqestDataException;
import com.huex.netflixbacked.exceptions.UnautorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MovieDetailsControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleUnauthorizeAccessException(UnautorizedAccessException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidRequestDataException(InvalidReqestDataException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInternalServerException(InternalServerException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUncaughtExceptions(Exception exception){
        return new ResponseEntity<>("Unhandled Exception: Please check logs for more details", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
