package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.entity.YearEntity;
import com.prismasolutions.DWbackend.mapper.YearMapper;
import com.prismasolutions.DWbackend.repository.YearRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@AllArgsConstructor
public class YearServiceImpl implements YearService{

    private final YearRepository yearRepository;
    private final YearMapper yearMapper;

    @Override
    public YearEntity create() {
        YearEntity yearEntity = new YearEntity();
        yearEntity.setStartYear(Calendar.getInstance().get(Calendar.YEAR));
        yearEntity.setEndYear(Calendar.getInstance().get(Calendar.YEAR)+1);

        YearEntity newyear = yearRepository.save(yearEntity);

        return newyear;
    }
}
