package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.PeriodEntity;

public interface DiplomaPeriodMappingService {

    void create(DiplomaEntity diplomaEntity, PeriodEntity periodEntity);
}
