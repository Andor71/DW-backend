package com.prismasolutions.DWbackend.dto.StudentDiplomaMappingDto;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Data
public class StudentDiplomaMappingDto {

    private Long id;
    private DiplomaDto diploma;
    private UserResponseDto student;
}
