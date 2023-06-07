package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.DepartmentEntity;
import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.DiplomaPeriodMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiplomaPeriodMappingRepository extends JpaRepository<DiplomaPeriodMappingEntity,Long> {
    boolean existsByPeriod_Major_DepartmentEntityAndDiploma_DiplomaId(DepartmentEntity departmentEntity, Long diplomaId);

    boolean existsByDiploma_DiplomaIdAndPeriod_Major_MajorId(Long diplomaId, Long majorId);
    List<DiplomaPeriodMappingEntity> findByDiploma_DiplomaId(Long diplomaId);
    void deleteByDiploma(DiplomaEntity diploma);
}
