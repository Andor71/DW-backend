package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "major")
@Data
public class MajorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "year")
    private String year;

    @Column(name = "programme")
    private String programme;

    @Column(name = "diploma_type")
    private String diplomaType;

    @Column(name = "start_year")
    private Date startYear;

    @Column(name = "end_year")
    private Date endYear;


}