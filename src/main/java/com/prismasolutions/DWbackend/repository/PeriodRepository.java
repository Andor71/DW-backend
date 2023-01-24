package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends JpaRepository<PeriodEntity, Long> {
}
