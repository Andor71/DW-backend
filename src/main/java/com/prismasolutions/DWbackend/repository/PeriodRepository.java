package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodRepository extends JpaRepository<PeriodEntity, Long> {
    PeriodEntity findByMajor_MajorId(Long majorId);

    PeriodEntity findByMajor_MajorIdAndYear_Id(Long majorId, Long id);

    boolean existsByMajor_MajorIdAndYear_Id(Long majorId, Long id);


    boolean existsByMajor_MajorId(Long majorId);

    List<PeriodEntity> findByYear_Id(Long id);







}
