package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "finished_student_diploma_mapping")
public class FinishedStudentDiplomaMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "FK_DIPLOMA_ID")
    private DiplomaEntity diploma;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "FK_STUDENT_ID")
    private UserEntity student;
}
