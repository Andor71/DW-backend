package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.FinishedSDMappingDto.FinishedSDMappingDto;
import com.prismasolutions.DWbackend.entity.FinishedStudentDiplomaMappingEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FinishedSDMappingMapper {

    private final DiplomaMapper diplomaMapper;
    private final UserMapper userMapper;

    public FinishedSDMappingDto toDto(FinishedStudentDiplomaMappingEntity entity){
        FinishedSDMappingDto dto = new FinishedSDMappingDto();

        dto.setId(entity.getId());
        dto.setDiploma(diplomaMapper.toDto(entity.getDiploma()));
        dto.setStudent(userMapper.toUserResponseDto(entity.getStudent()));
        dto.setAccepted(entity.getAccepted());

        return dto;
    }

    public List<FinishedSDMappingDto> toDtoList(List<FinishedStudentDiplomaMappingEntity> entities){
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public FinishedStudentDiplomaMappingEntity toEntity(FinishedSDMappingDto dto){
        FinishedStudentDiplomaMappingEntity entity = new FinishedStudentDiplomaMappingEntity();

        entity.setId(dto.getId());
        entity.setDiploma(diplomaMapper.toEntity(dto.getDiploma()));
        entity.setStudent(userMapper.toEntityFromResponse(dto.getStudent()));
        entity.setAccepted(dto.getAccepted());

        return entity;
    }

    public List<FinishedSDMappingDto> toEntityList(List<FinishedStudentDiplomaMappingEntity> entities){
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

}
