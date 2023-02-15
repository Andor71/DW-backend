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
    @JoinColumn(name = "fk_year_id", foreignKey = @ForeignKey(name = "fk_year_id"))
    private YearEntity year;

    @Column(name = "path", length = 64)
    private String path;

}