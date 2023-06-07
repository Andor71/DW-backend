package com.prismasolutions.DWbackend.entity;

import com.prismasolutions.DWbackend.enums.UserStatus;
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
    @Column(name = "active")
    private Boolean active;

    @Column(name = "role")
    private String role;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_department_id")
    private DepartmentEntity departmentEntity;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "major_id", foreignKey = @ForeignKey(name = "fk_teacher_mapping_id"))
    private MajorEntity major;
    @Column(name="media")
    private Double media;
    @Column(name="validation_code")
    private String validationCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
}
