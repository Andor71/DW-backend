package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.major.AllMajorDto;
import com.prismasolutions.DWbackend.dto.period.PeriodByYearDto;
import com.prismasolutions.DWbackend.dto.period.PeriodDto;
import com.prismasolutions.DWbackend.dto.year.YearDto;
import com.prismasolutions.DWbackend.entity.MajorEntity;
import com.prismasolutions.DWbackend.entity.PeriodEntity;
import com.prismasolutions.DWbackend.mapper.PeriodMapper;
import com.prismasolutions.DWbackend.mapper.YearMapper;
import com.prismasolutions.DWbackend.repository.MajorRepository;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import com.prismasolutions.DWbackend.repository.YearRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PeriodServiceImpl implements PeriodService{

    private final PeriodRepository periodRepository;
    private final PeriodMapper periodMapper;

    private final YearMapper yearMapper;
    private final YearRepository yearRepository;
    private final MajorRepository majorRepository;

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
        if(periodDto.getDiplomaDefend() == null) {
            throw new IllegalArgumentException("Diploma defend cannot be null!");
        }
        if(periodDto.getMajor() == null){
            throw new IllegalArgumentException("Major cannot be null!");
        }

        Optional<MajorEntity> major = majorRepository.findById(periodDto.getMajor().getMajorId());

        if(major.isEmpty()){
            throw new EntityNotFoundException("No major found with this id!");
        }

        PeriodEntity periodEntityOptional = periodRepository.findByMajor_MajorId(periodDto.getMajor().getMajorId());

        if(periodEntityOptional != null){
            throw new IllegalArgumentException("This major already has a period!");
        }

        PeriodEntity newPeriod = periodRepository.save(periodMapper.toEntity(periodDto));

        return periodMapper.toDto(newPeriod);
    }

    @Override
    public PeriodDto update(PeriodDto periodDto) {
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
        if(periodDto.getDiplomaDefend() == null) {
            throw new IllegalArgumentException("Diploma defend cannot be null!");
        }
        if(periodDto.getMajor() == null){
            throw new IllegalArgumentException("Major cannot be null!");
        }

        PeriodEntity periodEntity = periodRepository.findById(periodDto.getPeriodId()).orElseThrow(()->{
            throw new EntityNotFoundException("No period found whit this id!");
        });

        Optional<MajorEntity> major = majorRepository.findById(periodDto.getMajor().getMajorId());

        if(major.isEmpty()){
            throw new EntityNotFoundException("No major found whit this id!");
        }

        PeriodEntity newPeriod = periodRepository.save(periodMapper.toEntity(periodDto));

        return periodMapper.toDto(newPeriod);
    }

    @Override
    public PeriodDto getPeriodByMajorId(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }
        PeriodEntity periodEntity = periodRepository.findByMajor_MajorId(id);

        if(periodEntity == null){
            throw new EntityNotFoundException("Entity not found!");
        }

        return periodMapper.toDto(periodEntity);
    }

    @Override
    public List<PeriodDto> getAllActivePeriod() {
        return periodMapper.toDtoList(periodRepository.findAll());
    }

    @Override
    public Long delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        PeriodEntity periodEntity = periodRepository.findByMajor_MajorId(id);

        if(periodEntity == null){
            throw new EntityNotFoundException("No entity found with this id!");
        }

        periodRepository.delete(periodEntity);
        return id;
    }

    @Override
    public PeriodDto getById(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }
        Optional<PeriodEntity> periodEntityOptional = periodRepository.findById(id);

        if(periodEntityOptional.isEmpty()){
            throw new EntityNotFoundException("No entity found with this id!");
        }

        return periodMapper.toDto(periodEntityOptional.get());
    }

    @Override
    public List<PeriodByYearDto> getAllPeriodsByYear() {

        List<YearDto> yearDtos = yearMapper.toDtoList(yearRepository.findAll());

        ArrayList<PeriodByYearDto> periodByYearDtos = new ArrayList<>();

        for(YearDto yearDto: yearDtos){
            List<PeriodDto> periodDtos = periodMapper.toDtoList(periodRepository.findByYear_Id(yearDto.getId()));
            PeriodByYearDto periodByYearDto = new PeriodByYearDto();
            periodByYearDto.setPeriods(periodDtos);
            periodByYearDto.setYear(yearDto);

            periodByYearDtos.add(periodByYearDto);
        }

        return periodByYearDtos;
    }

    @Override
    public PeriodDto getCurrentPeriodForMajor(Long majorID) {

        if(majorID == null){
            throw new IllegalArgumentException("Major id cannot be null!");
        }

        MajorEntity majorEntity = majorRepository.findById(majorID).orElseThrow(()->{
            throw new EntityNotFoundException("Entity not found!");
        });




        return null;
    }
}
