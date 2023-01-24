package com.prismasolutions.DWbackend.dto.user;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String image;
    private Boolean active;
}
