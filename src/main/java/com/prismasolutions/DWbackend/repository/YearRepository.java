package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.YearEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearRepository extends JpaRepository<YearEntity,Long> {
    YearEntity findByStartYearAndEndYear(Integer startYear, Integer endYear);

    boolean existsByStartYearAndEndYear(Integer startYear, Integer endYear);
}
