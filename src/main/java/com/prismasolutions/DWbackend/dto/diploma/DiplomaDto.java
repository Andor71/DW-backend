package com.prismasolutions.DWbackend.dto.diploma;

import com.prismasolutions.DWbackend.dto.user.UserDto;
import com.prismasolutions.DWbackend.entity.UserEntity;
import lombok.Data;

import java.time.Period;

@Data
public class DiplomaDto {
    private Long diplomaId;

    private String title;

    private Period period;

    private UserDto student;

    private Double score;

    private String stage;

    private UserDto teacher;

    private Integer visibility;

    private String keyWords;

    private String type;

    private Boolean taken;

    private String abstractDoc;

    private String description;
}
