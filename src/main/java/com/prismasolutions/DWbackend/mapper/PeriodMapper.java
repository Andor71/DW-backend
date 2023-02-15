package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.period.PeriodDto;
import com.prismasolutions.DWbackend.entity.PeriodEntity;
import com.prismasolutions.DWbackend.repository.MajorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PeriodMapper {

    private final MajorMapper majorMapper;

    public PeriodDto toDto(PeriodEntity periodEntity){
        PeriodDto periodDto = new PeriodDto();

        periodDto.setPeriodId(periodEntity.getPeriodId());
        periodDto.setStartOfEnteringTopics(periodEntity.getStartOfEnteringTopics());
        periodDto.setEndOfEnteringTopics(periodEntity.getEndOfEnteringTopics());
        periodDto.setFirstTopicAdvertisement(periodEntity.getFirstTopicAdvertisement());
        periodDto.setFirstTopicAdvertisementEnd(periodEntity.getFirstTopicAdvertisementEnd());
        periodDto.setFirstAllocation(periodEntity.getFirstAllocation());
        periodDto.setSecondTopicAdvertisement(periodEntity.getSecondTopicAdvertisement());
        periodDto.setSecondTopicAdvertisementEnd(periodEntity.getSecondTopicAdvertisementEnd());
        periodDto.setImplementationOfTopics(periodEntity.getImplementationOfTopics());
        periodDto.setDocumentumUpload(periodEntity.getDocumentumUpload());
        periodDto.setDiplomaDefend(periodEntity.getDiplomaDefend());
        if(periodEntity.getMajor() != null){
            periodDto.setMajor(majorMapper.toDto(periodEntity.getMajor()));
        }
        return periodDto;
    }
    public List<PeriodDto> toDtoList(List<PeriodEntity> periodEntities){
        return periodEntities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public PeriodEntity toEntity(PeriodDto periodDto){
        PeriodEntity periodEntity = new PeriodEntity();

        periodEntity.setPeriodId(periodDto.getPeriodId());
        periodEntity.setStartOfEnteringTopics(periodDto.getStartOfEnteringTopics());
        periodEntity.setEndOfEnteringTopics(periodDto.getEndOfEnteringTopics());
        periodEntity.setFirstTopicAdvertisement(periodDto.getFirstTopicAdvertisement());
        periodEntity.setFirstTopicAdvertisementEnd(periodDto.getFirstTopicAdvertisementEnd());
        periodEntity.setFirstAllocation(periodDto.getFirstAllocation());
        periodEntity.setSecondTopicAdvertisement(periodDto.getSecondTopicAdvertisement());
        periodEntity.setSecondTopicAdvertisementEnd(periodDto.getSecondTopicAdvertisementEnd());
        periodEntity.setImplementationOfTopics(periodDto.getImplementationOfTopics());
        periodEntity.setDocumentumUpload(periodDto.getDocumentumUpload());
        periodEntity.setDiplomaDefend(periodDto.getDiplomaDefend());

        periodEntity.setMajor(majorMapper.toEntity(periodDto.getMajor()));

        return periodEntity;
    }
    public List<PeriodEntity> toEntityList(List<PeriodDto> periodDtos){
        return periodDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}

