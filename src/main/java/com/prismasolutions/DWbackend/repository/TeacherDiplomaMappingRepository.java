package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.DepartmentEntity;
import com.prismasolutions.DWbackend.entity.TeacherDiplomaMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDiplomaMappingRepository extends JpaRepository<TeacherDiplomaMappingEntity, Long> {
}
