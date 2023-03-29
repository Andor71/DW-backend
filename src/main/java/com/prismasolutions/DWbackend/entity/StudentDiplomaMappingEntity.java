package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "student_diploma_mapping")
@Data
public class StudentDiplomaMappingEntity {
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

    @Column(name = "priority")
    private Integer priority;
}
