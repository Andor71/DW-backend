package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.year.YearDto;
import com.prismasolutions.DWbackend.entity.YearEntity;

public interface YearService {

    YearDto create();

    YearDto getCurrent();

    YearDto getById(Long id);
}
