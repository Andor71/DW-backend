package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.MajorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<MajorEntity, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM period WHERE fk_major_id =:fk_major_id", nativeQuery = true)
    boolean exists(@Param("fk_major_id") Integer majorID);

    List<MajorEntity> findByYear_StartYearBetweenAndYear_EndYearBetween(Integer startYearStart, Integer startYearEnd, Integer endYearStart, Integer endYearEnd);
    List<MajorEntity> findByYear_Id(Long id);




}
