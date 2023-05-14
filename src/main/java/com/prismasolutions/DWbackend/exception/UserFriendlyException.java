package com.prismasolutions.DWbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserFriendlyException extends RuntimeException{
    public UserFriendlyException(String message)  {
        super(message);
    }

}