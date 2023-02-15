package com.prismasolutions.DWbackend.service;


import com.prismasolutions.DWbackend.dto.period.PeriodDto;

import java.util.List;

public interface PeriodService {

    PeriodDto create(PeriodDto periodDto);

    PeriodDto update(PeriodDto periodDto);

    PeriodDto getPeriodByMajorId(Long id);

    List<PeriodDto> getAllActivePeriod();

    Long delete(Long id);

    PeriodDto getById(Long id);
}
