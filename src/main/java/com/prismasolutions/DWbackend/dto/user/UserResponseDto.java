package com.prismasolutions.DWbackend.dto.user;

import com.prismasolutions.DWbackend.dto.department.DepartmentDto;
import com.prismasolutions.DWbackend.dto.major.MajorDto;
import com.prismasolutions.DWbackend.enums.UserStatus;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Boolean active;
    private Double media;
    private MajorDto majorDto;
    private UserStatus status;
    private DepartmentDto department;
}
