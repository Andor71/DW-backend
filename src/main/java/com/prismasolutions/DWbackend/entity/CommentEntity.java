package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
@Data
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "diploma_id", foreignKey = @ForeignKey(name = "fk_diploma_id"))
    private DiplomaEntity diploma;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id"))
    private UserEntity user;
    @Column(name = "score")
    private Integer score;
    @Column(name = "date")
    private Date date;
    @Column(name = "viewed")
    private Boolean viewed;

}