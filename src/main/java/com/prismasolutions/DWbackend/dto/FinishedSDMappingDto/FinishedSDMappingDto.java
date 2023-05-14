package com.prismasolutions.DWbackend.dto.FinishedSDMappingDto;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import lombok.Data;

@Data
public class FinishedSDMappingDto {
    private Long id;
    private DiplomaDto diploma;
    private UserResponseDto student;
    private Boolean accepted;
}
