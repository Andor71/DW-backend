package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.FinishedStudentDiplomaMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Finished_D_S_MRepository extends JpaRepository<FinishedStudentDiplomaMappingEntity,Long> {
    boolean existsByDiploma_DiplomaIdAndStudent_Id(Long diplomaId, Long id);

    FinishedStudentDiplomaMappingEntity findByDiploma_DiplomaIdAndStudent_Id(Long diplomaId, Long id);

}
