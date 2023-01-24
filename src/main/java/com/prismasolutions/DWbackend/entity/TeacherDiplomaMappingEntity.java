package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "teacher_diploma_mapping")
@Data
public class TeacherDiplomaMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "diploma_id", foreignKey = @ForeignKey(name = "fk_diploma_id"))
    private DiplomaEntity diploma;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "teacher_id", foreignKey = @ForeignKey(name = "fk_teacher_mapping_id"))
    private UserEntity teacher;

}