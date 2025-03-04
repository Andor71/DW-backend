package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.DiplomaFilesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiplomaFilesRepository extends JpaRepository<DiplomaFilesEntity, Long> {
    DiplomaFilesEntity findByDiploma_DiplomaIdAndAuthor_Id(Long diplomaId, Long id);

    DiplomaFilesEntity findByAuthor_Id(Long id);




}
