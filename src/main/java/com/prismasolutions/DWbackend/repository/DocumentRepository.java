package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    DocumentEntity findByNameAndYear_Id(String name, Long id);

    List<DocumentEntity> findByYear_Id(Long id);


}
