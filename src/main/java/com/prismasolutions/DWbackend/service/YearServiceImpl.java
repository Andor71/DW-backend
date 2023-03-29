package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.year.YearDto;
import com.prismasolutions.DWbackend.entity.YearEntity;
import com.prismasolutions.DWbackend.mapper.YearMapper;
import com.prismasolutions.DWbackend.repository.YearRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;

@Service
@AllArgsConstructor
public class YearServiceImpl implements YearService{

    private final YearRepository yearRepository;
    private final YearMapper yearMapper;

    @Override
    public YearDto create() {
        YearEntity yearEntity = new YearEntity();
        yearEntity.setStartYear(Calendar.getInstance().get(Calendar.YEAR));
        yearEntity.setEndYear(Calendar.getInstance().get(Calendar.YEAR)+1);

        YearEntity newyear = yearRepository.save(yearEntity);

        return yearMapper.toDto(newyear);
    }

    @Override
    public YearDto getCurrent() {
        return yearMapper.toDto(yearRepository.findByStartYearAndEndYear(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.YEAR)+1));
    }

    @Override
    public YearDto getById(Long id) {

        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }
        YearEntity yearEntity = yearRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException("Entity not found!");
        });

        return yearMapper.toDto(yearEntity);
    }
}
