package com.prismasolutions.DWbackend.dto.user;

import lombok.Data;

@Data
public class PasswordDto {
    String password;
    String validationCode;
}
