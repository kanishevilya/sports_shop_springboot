package com.example.SpringBootProject.Exceptions;

import com.example.SpringBootProject.enums.ErrorCode;
import lombok.Getter;

public class CustomException extends RuntimeException{

    @Getter
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message){
        super(message);
        this.errorCode=errorCode;
    }
}
