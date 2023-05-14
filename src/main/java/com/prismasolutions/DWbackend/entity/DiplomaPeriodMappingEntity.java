package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "diploma_period_mapping")
@Data
public class DiplomaPeriodMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_diploma_id")
    private DiplomaEntity diploma;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_period_id")
    private PeriodEntity period;

}
