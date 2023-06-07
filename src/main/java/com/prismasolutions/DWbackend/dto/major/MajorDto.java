package com.prismasolutions.DWbackend.dto.major;

import com.prismasolutions.DWbackend.dto.department.DepartmentDto;
import com.prismasolutions.DWbackend.dto.year.YearDto;
import lombok.Data;

@Data
public class MajorDto {

    private Long majorId;

    private YearDto year;

    private String programme;

    private String diplomaType;
}
