package com.prismasolutions.DWbackend.dto.errorResponse;

public class ErrorResponseDto extends Exception{
    public ErrorResponseDto(String errorMessage) {
        super(errorMessage);
    }
}
