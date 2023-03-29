package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiplomaRepository extends JpaRepository<DiplomaEntity, Long> {
    List<DiplomaEntity> findByTeacher_Id(Long id);

    List<DiplomaEntity> findByVisibility(Integer visibility);

    List<DiplomaEntity> findByStudentNotNull();



}
