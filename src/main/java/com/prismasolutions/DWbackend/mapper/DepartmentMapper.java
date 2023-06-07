package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.department.DepartmentDto;
import com.prismasolutions.DWbackend.entity.DepartmentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper {

    public DepartmentDto toDto(DepartmentEntity departmentEntity){
        DepartmentDto dto = new DepartmentDto();

        dto.setName(departmentEntity.getName());
        dto.setDepartmentId(departmentEntity.getDepartmentId());
        dto.setFaculty(departmentEntity.getFaculty());

        return dto;
    }

    public List<DepartmentDto> toDtoList(List<DepartmentEntity> departmentEntities){
        return departmentEntities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DepartmentEntity toEntity(DepartmentDto departmentDto){
        DepartmentEntity entity = new DepartmentEntity();

        entity.setName(departmentDto.getName());
        entity.setDepartmentId(departmentDto.getDepartmentId());
        entity.setFaculty(departmentDto.getFaculty());

        return entity;
    }

    public List<DepartmentEntity> toEntityList(List<DepartmentDto> departmentDtos){
        return departmentDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
