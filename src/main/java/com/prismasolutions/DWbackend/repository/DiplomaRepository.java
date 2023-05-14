package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiplomaRepository extends JpaRepository<DiplomaEntity, Long> {

    List<DiplomaEntity> findByVisibility(Integer visibility);

    List<DiplomaEntity> findByStudentNotNull();

    List<DiplomaEntity> findByStudentNull();

    DiplomaEntity findByStudent_Id(Long id);

    boolean existsByStudent_IdAndDiplomaId(Long id, Long diplomaId);



}
