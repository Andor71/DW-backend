package com.prismasolutions.DWbackend.dto.major;

import lombok.Data;

import java.util.Date;

@Data
public class MajorDto {

    private Long majorId;

    private String year;

    private String programme;

    private String diplomaType;

    private Date startYear;

    private Date endYear;

}
