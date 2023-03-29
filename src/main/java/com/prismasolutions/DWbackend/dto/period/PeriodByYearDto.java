package com.prismasolutions.DWbackend.dto.period;

import com.prismasolutions.DWbackend.dto.year.YearDto;
import lombok.Data;

import java.util.List;


@Data
public class PeriodByYearDto {
    YearDto year;
    List<PeriodDto> periods;
}
