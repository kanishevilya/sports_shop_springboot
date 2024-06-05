package com.example.SpringBootProject.handler;

import com.example.SpringBootProject.Exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> customExceptionHandler(Exception e) {
        if(e instanceof CustomException) {
            switch (((CustomException) e).getErrorCode()){
                case VALIDATION_ERROR:
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                case SERVER_ERROR:
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                case NOT_FOUND:
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
                default:
                    return new ResponseEntity<>(
                            "Unhandled exception "+e.getMessage(), HttpStatus.NOT_IMPLEMENTED
                    );
            }
        }
        return new ResponseEntity<>(Arrays.toString(e.getStackTrace()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
