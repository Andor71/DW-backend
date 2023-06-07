package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.major.AllMajorDto;
import com.prismasolutions.DWbackend.dto.major.MajorDto;
import com.prismasolutions.DWbackend.dto.period.PeriodByYearDto;
import com.prismasolutions.DWbackend.dto.year.YearDto;
import com.prismasolutions.DWbackend.entity.MajorEntity;
import com.prismasolutions.DWbackend.entity.PeriodEntity;
import com.prismasolutions.DWbackend.entity.YearEntity;
import com.prismasolutions.DWbackend.mapper.MajorMapper;
import com.prismasolutions.DWbackend.mapper.YearMapper;
import com.prismasolutions.DWbackend.repository.MajorRepository;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import com.prismasolutions.DWbackend.repository.YearRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MajorServiceImpl implements MajorService{

    private final MajorRepository majorRepository;
    private final MajorMapper majorMapper;
    private final PeriodRepository periodRepository;
    private final YearService yearService;
    private final YearRepository yearRepository;
    private final YearMapper yearMapper;

    @Override
    public List<MajorDto> getAllWithoutPeriod(Long yearID) {
        if(yearID == null){
            throw new IllegalArgumentException("Id canot be null!");
        }
        YearEntity yearEntity = yearRepository.findById(yearID).orElseThrow(()->{
            throw new IllegalArgumentException("Enity not found!");
        });

        List<MajorEntity> majorEntitiesAll = majorRepository.findAll();

        List<MajorEntity> majorEntitiesWithoutPeriod = new ArrayList<>();

        majorEntitiesAll.forEach((x)->{
            if(!periodRepository.existsByMajor_MajorIdAndYear_Id(x.getMajorId(),yearEntity.getId())){
                majorEntitiesWithoutPeriod.add(x);
            }
        });
        return majorMapper.toDtoList(majorEntitiesWithoutPeriod);
    }

    @Override
    public List<MajorDto> getAll() {
        return  majorMapper.toDtoList(majorRepository.findAll());
    }


}
