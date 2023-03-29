package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "diploma_files")
@Data
public class DiplomaFilesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diploma_files_id")
    private Long diplomaFilesId;

    @Column(name = "title", length = 64)
    private String title;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "diploma_id", foreignKey = @ForeignKey(name = "fk_diploma_id"))
    private DiplomaEntity diploma;

    @Column(name = "type", length = 64)
    private String type;

    @Column(name = "path", length = 64)
    private String path;

    @Column(name = "visibility")
    private Integer visibility;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "fk_author_id"))
    private UserEntity author;

}