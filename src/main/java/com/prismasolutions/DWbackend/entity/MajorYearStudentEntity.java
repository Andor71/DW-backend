package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "major_year_student_mapping")
@Data
public class MajorYearStudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_major_id")
    private MajorEntity major;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "FK_STUDENT_ID")
    private UserEntity student;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_year_id")
    private YearEntity year;
}
