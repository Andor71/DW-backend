package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;

public interface TeacherDiplomaMappingService {
    void create(UserEntity userEntity, DiplomaEntity diplomaEntity);
}
