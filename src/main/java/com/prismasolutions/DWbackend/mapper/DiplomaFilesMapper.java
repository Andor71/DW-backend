package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.diplomaFiles.DiplomaFilesDto;
import com.prismasolutions.DWbackend.entity.DiplomaFilesEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DiplomaFilesMapper {

    private final DiplomaMapper diplomaMapper;
    private final UserMapper userMapper;

    public DiplomaFilesDto toDto(DiplomaFilesEntity diplomaFilesEntity){
        DiplomaFilesDto diplomaFilesDto = new DiplomaFilesDto();

        diplomaFilesDto.setDiplomaFilesId(diplomaFilesEntity.getDiplomaFilesId());
        diplomaFilesDto.setDiploma(diplomaMapper.toDto(diplomaFilesEntity.getDiploma()));
        diplomaFilesDto.setPath(diplomaFilesEntity.getPath());
        diplomaFilesDto.setVisibility(diplomaFilesEntity.getVisibility());
        diplomaFilesDto.setAuthor(userMapper.toDto(diplomaFilesEntity.getAuthor()));
        diplomaFilesDto.setTitle(diplomaFilesEntity.getTitle());

        return diplomaFilesDto;
    }

    public List<DiplomaFilesDto> toDtoList(List<DiplomaFilesEntity> diplomaFilesEntities){
        return diplomaFilesEntities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DiplomaFilesEntity toEntity(DiplomaFilesDto diplomaFilesDto){
        DiplomaFilesEntity diplomaFilesEntity = new DiplomaFilesEntity();

        diplomaFilesEntity.setDiplomaFilesId(diplomaFilesDto.getDiplomaFilesId());
        diplomaFilesEntity.setDiploma(diplomaMapper.toEntity(diplomaFilesDto.getDiploma()));
        diplomaFilesEntity.setPath(diplomaFilesDto.getPath());
        diplomaFilesEntity.setVisibility(diplomaFilesDto.getVisibility());
        diplomaFilesEntity.setAuthor(userMapper.toEntity(diplomaFilesDto.getAuthor()));
        diplomaFilesEntity.setTitle(diplomaFilesDto.getTitle());

        return diplomaFilesEntity;
    }

    public List<DiplomaFilesEntity> toEntityList(List<DiplomaFilesDto> diplomaFilesDtos){
        return diplomaFilesDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
