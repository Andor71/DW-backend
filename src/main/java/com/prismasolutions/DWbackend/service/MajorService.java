package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.major.AllMajorDto;
import com.prismasolutions.DWbackend.dto.major.MajorDto;
import com.prismasolutions.DWbackend.dto.period.PeriodByYearDto;

import java.util.List;

public interface MajorService {
    List<MajorDto> getAllWithoutPeriod(Long yearID);

    List<MajorDto> getAll();
}
