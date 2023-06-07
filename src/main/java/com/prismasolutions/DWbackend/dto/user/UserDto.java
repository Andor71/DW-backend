package com.prismasolutions.DWbackend.dto.user;

import com.prismasolutions.DWbackend.enums.UserStatus;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private String token;
    private Boolean active;
    private String validationCode;
    private UserStatus status;
}
