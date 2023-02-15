package com.prismasolutions.DWbackend.dto.major;

import com.prismasolutions.DWbackend.dto.year.YearDto;
import lombok.Data;

import java.util.List;

@Data
public class AllMajorDto {
    YearDto year;
    List<MajorDto> majors;
}
