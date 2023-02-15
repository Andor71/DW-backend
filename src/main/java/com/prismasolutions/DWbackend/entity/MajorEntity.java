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

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_year_id")
    private YearEntity year;

    @Column(name = "programme")
    private String programme;

    @Column(name = "diploma_type")
    private String diplomaType;

}