package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "year")
@Data
public class YearEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "year_id")
    Long id;
    @Column(name = "start_year")
    Integer startYear;
    @Column(name = "end_year")
    Integer endYear;
}
