package com.prismasolutions.DWbackend.entity;

import com.prismasolutions.DWbackend.enums.DiplomaStages;
import lombok.Data;

import javax.persistence.*;
import java.time.Period;

@Entity
@Table(name = "diploma")
@Data
public class DiplomaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diploma_id")
    private Long diplomaId;

    @Column(name = "title", length = 512)
    private String title;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name = "fk_student_id"))
    private UserEntity student;

    @Column(name = "score")
    private Double score;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage", length = 64)
    private DiplomaStages stage;

    @Column(name = "visibility")
    private Integer visibility;

    @Column(name = "key_words", length = 64)
    private String keyWords;

    @Column(name = "type", length = 64)
    private String type;

    @Column(name = "taken")
    private Boolean taken;

    @Column(name = "abstract", columnDefinition = "text")
    private String abstractDoc;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "details")
    private String details;
    @Column(name="necessary_knowledge")
    private String necessaryKnowledge;

    @Column(name = "different_expectations")
    private String differentExpectations;

    @Column(name="bibliography")
    private String bibliography;
}
