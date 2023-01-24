package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiplomaRepository extends JpaRepository<DiplomaEntity, Long> {
}
