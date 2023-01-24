package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "document")
@Data
public class DocumentEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "name", length = 64)
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "major_id", foreignKey = @ForeignKey(name = "fk_major_id"))
    private MajorEntity major;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_period", foreignKey = @ForeignKey(name = "fk_period_id"))
    private PeriodEntity period;

    @Column(name = "path", length = 64)
    private String path;

}