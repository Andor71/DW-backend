package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.TeacherDiplomaMappingEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.repository.TeacherDiplomaMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeacherDiplomaMappingServiceImpl implements TeacherDiplomaMappingService{

    private final TeacherDiplomaMappingRepository teacherDiplomaMappingRepository;

    @Override
    public void create(UserEntity userEntity, DiplomaEntity diplomaEntity) {
        TeacherDiplomaMappingEntity teacherDiplomaMappingEntity = new TeacherDiplomaMappingEntity();
        teacherDiplomaMappingEntity.setDiploma(diplomaEntity);
        teacherDiplomaMappingEntity.setTeacher(userEntity);

        teacherDiplomaMappingRepository.save(teacherDiplomaMappingEntity);
    }
}
