package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.major.AllMajorDto;
import com.prismasolutions.DWbackend.dto.major.MajorDto;

import java.util.List;

public interface MajorService {
    List<MajorDto> getAllWithoutPeriod();

    List<MajorDto>  create();

    List<AllMajorDto> getAll();
}
