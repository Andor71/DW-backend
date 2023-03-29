package com.prismasolutions.DWbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoAuthority extends RuntimeException{
    public NoAuthority(String message)  {
        super(message);
    }

    public NoAuthority(String message, Throwable cause) {
        super(message, cause);
    }
}
