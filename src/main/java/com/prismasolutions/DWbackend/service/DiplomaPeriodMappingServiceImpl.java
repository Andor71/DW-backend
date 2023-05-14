package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.DiplomaPeriodMappingEntity;
import com.prismasolutions.DWbackend.entity.PeriodEntity;
import com.prismasolutions.DWbackend.repository.DiplomaPeriodMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiplomaPeriodMappingServiceImpl implements DiplomaPeriodMappingService{

    private final DiplomaPeriodMappingRepository diplomaPeriodMappingRepository;

    @Override
    public void create(DiplomaEntity diplomaEntity, PeriodEntity periodEntity) {
        DiplomaPeriodMappingEntity diplomaPeriodMappingEntity = new DiplomaPeriodMappingEntity();
        diplomaPeriodMappingEntity.setDiploma(diplomaEntity);
        diplomaPeriodMappingEntity.setPeriod(periodEntity);

        diplomaPeriodMappingRepository.save(diplomaPeriodMappingEntity);
    }
}
