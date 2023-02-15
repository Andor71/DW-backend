package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.year.YearDto;
import com.prismasolutions.DWbackend.entity.YearEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class YearMapper {

    public YearDto toDto(YearEntity yearEntity){
        YearDto yearDto = new YearDto();

        yearDto.setId(yearEntity.getId());
        yearDto.setFirstYear(yearEntity.getStartYear());
        yearDto.setSecondYear(yearEntity.getEndYear());

        return yearDto;
    }

    public List<YearDto> toDtoList(List<YearEntity> yearEntities){
        return yearEntities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public YearEntity toEntity(YearDto yearDto){
        YearEntity yearEntity = new YearEntity();

        yearEntity.setId(yearDto.getId());
        yearEntity.setStartYear(yearDto.getFirstYear());
        yearEntity.setEndYear(yearDto.getSecondYear());

        return yearEntity;
    }

    public List<YearEntity> toEntityList(List<YearDto> yearDtos){
        return yearDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }


}
