package com.prismasolutions.DWbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_image")
    private String userImage;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "role")
    private String role;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "major_id", foreignKey = @ForeignKey(name = "fk_teacher_mapping_id"))
    private MajorEntity major;

    @Column(name="media")
    private Double media;

    @Column(name = "status")
    private Integer status;
}
