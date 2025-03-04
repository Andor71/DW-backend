package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Period;
import java.util.Date;

@Entity
@Table(name = "major")
@Data
public class MajorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "programme")
    private String programme;

    @JoinColumn(name = "fk_department_id")
    @OneToOne(cascade = CascadeType.DETACH)
    private DepartmentEntity departmentEntity;

    @Column(name = "diploma_type")
    private String diplomaType;

}