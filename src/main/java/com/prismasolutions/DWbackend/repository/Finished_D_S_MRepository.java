package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.FinishedStudentDiplomaMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Finished_D_S_MRepository extends JpaRepository<FinishedStudentDiplomaMappingEntity,Long> {
    long countByIdNotNull();
    boolean existsByDiploma_DiplomaId(Long diplomaId);

    Optional<FinishedStudentDiplomaMappingEntity> findByDiploma_DiplomaIdAndStudent_Id(Long diplomaId, Long id);




}
