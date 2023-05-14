package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.MajorYearStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorYearStudentMappingRepository extends JpaRepository<MajorYearStudentEntity,Long> {
    List<MajorYearStudentEntity> findByMajor_MajorIdAndYear_Id(Long majorId, Long id);


}
