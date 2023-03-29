package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.StudentDiplomaMappingEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StudentDiplomaMappingRepository extends JpaRepository<StudentDiplomaMappingEntity, Long> {
    boolean existsByDiploma_DiplomaIdAndStudent_Id(Long diplomaId, Long id);

    List<StudentDiplomaMappingEntity> findByStudent_Id(Long id);

    void deleteByStudent(UserEntity student);

    StudentDiplomaMappingEntity findByDiploma_DiplomaIdAndStudent_Id(Long diplomaId, Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( value ="DELETE FROM student_diploma_mapping WHERE FK_STUDENT_ID = :FK_STUDENT_ID and  FK_DIPLOMA_ID = :FK_DIPLOMA_ID",nativeQuery = true)
    void removeFromStudentGroupMapping(@Param("FK_STUDENT_ID") Integer FK_STUDENT_ID, @Param("FK_DIPLOMA_ID") Integer FK_DIPLOMA_ID);

    List<StudentDiplomaMappingEntity> findByStudent_IdOrderByPriorityAsc(Long id);

    List<StudentDiplomaMappingEntity> findByDiploma_DiplomaId(Long diplomaId);

}
