package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.MajorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<MajorEntity, Long> {
}
