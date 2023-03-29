package com.prismasolutions.DWbackend.dto.user;

import com.prismasolutions.DWbackend.dto.major.MajorDto;
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
    private Double media;
    private MajorDto majorDto;
}
