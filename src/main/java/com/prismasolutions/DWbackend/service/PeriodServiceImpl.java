package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.period.PeriodDto;
import com.prismasolutions.DWbackend.entity.PeriodEntity;
import com.prismasolutions.DWbackend.mapper.PeriodMapper;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PeriodServiceImpl implements PeriodService{

    private final PeriodRepository periodRepository;
    private final PeriodMapper periodMapper;

    @Override
    public PeriodDto create(PeriodDto periodDto) {
        if(periodDto.getStartOfEnteringTopics() == null){
            throw new IllegalArgumentException("StartOfEnteringTopics cannot be null!");
        }
        if(periodDto.getEndOfEnteringTopics() == null){
            throw new IllegalArgumentException("EndOfEnteringTopics cannot be null!");
        }
        if(periodDto.getFirstTopicAdvertisement() == null){
            throw new IllegalArgumentException("FirstTopicAdvertisement cannot be null!");
        }
        if(periodDto.getFirstTopicAdvertisementEnd() == null){
            throw new IllegalArgumentException("FirstTopicAdvertisementEnd cannot be null!");
        }
        if(periodDto.getFirstAllocation() == null){
            throw new IllegalArgumentException("FirstAllocation cannot be null!");
        }
        if(periodDto.getSecondTopicAdvertisement() == null){
            throw new IllegalArgumentException("SecondTopicAdvertisement defend cannot be null!");
        }
        if(periodDto.getDocumentumUpload() == null){
            throw new IllegalArgumentException("Documentum Upload cannot be null!");
        }
        if(periodDto.getSecondTopicAdvertisementEnd() == null){
            throw new IllegalArgumentException("SecondTopicAdvertisementEnd cannot be null!");
        }
        if(periodDto.getImplementationOfTopics() == null){
            throw new IllegalArgumentException("ImplementationOfTopics cannot be null!");
        }
        if(periodDto.getDocumentumUpload() == null){
            throw new IllegalArgumentException("DocumentumUpload cannot be null!");
        }
        if(periodDto.getDiplomaDefend() == null) {
            throw new IllegalArgumentException("Diploma defend cannot be null!");
        }
        PeriodEntity newPeriod = periodRepository.save(periodMapper.toEntity(periodDto));

        return periodMapper.toDto(newPeriod);
    }

    @Override
    public PeriodDto update(PeriodDto periodDto) {
        return null;
    }

    @Override
    public PeriodDto getPeriodById(Long id) {
        return null;
    }

    @Override
    public List<PeriodDto> getAllActivePeriod() {
        return periodMapper.toDtoList(periodRepository.findAll());
    }
}
