package com.prismasolutions.DWbackend.dto.finishedDSMDto;

import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import lombok.Data;

import javax.persistence.*;
@Data
public class FinishedDSMDto {
    private Long id;
    private DiplomaEntity diploma;
    private UserEntity student;
}
