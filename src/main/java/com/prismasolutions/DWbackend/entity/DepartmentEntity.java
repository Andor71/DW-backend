package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "department")
@Data
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "faculty", length = 64)
    private String faculty;

    @Column(name = "name", length = 64)
    private String name;

}