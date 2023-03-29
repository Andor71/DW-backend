package com.prismasolutions.DWbackend.repository;

import com.prismasolutions.DWbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByActiveAndRole(Boolean active, String role);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query( value ="SELECT * FROM users WHERE user_id IN ( SELECT fk_student_id  FROM student_diploma_mapping )",nativeQuery = true)
    List<UserEntity> getAllUsersFromDiplomaMapping();


}
