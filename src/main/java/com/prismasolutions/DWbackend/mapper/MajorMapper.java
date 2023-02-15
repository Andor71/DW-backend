package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.major.MajorDto;
import com.prismasolutions.DWbackend.entity.MajorEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MajorMapper {

    private final YearMapper yearMapper;
    public MajorDto toDto(MajorEntity majorEntity){
        MajorDto majorDto = new MajorDto();

        majorDto.setMajorId(majorEntity.getMajorId());
        majorDto.setYear(yearMapper.toDto(majorEntity.getYear()));
        majorDto.setProgramme(majorEntity.getProgramme());
        majorDto.setDiplomaType(majorEntity.getDiplomaType());

        return majorDto;
    }

    public List<MajorDto> toDtoList(List<MajorEntity> majorEntities){
        return majorEntities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public MajorEntity toEntity(MajorDto majorDto){
        MajorEntity majorEntity = new MajorEntity();

        majorEntity.setYear(yearMapper.toEntity(majorDto.getYear()));
        majorEntity.setMajorId(majorDto.getMajorId());
        majorEntity.setProgramme(majorDto.getProgramme());
        majorEntity.setDiplomaType(majorDto.getDiplomaType());

        return majorEntity;
    }

    public List<MajorEntity> toEntityList(List<MajorDto> majorDtos){
        return majorDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
