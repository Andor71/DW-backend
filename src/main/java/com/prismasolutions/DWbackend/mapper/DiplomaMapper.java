package com.prismasolutions.DWbackend.mapper;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.repository.PeriodRepository;
import com.prismasolutions.DWbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DiplomaMapper {

    private final PeriodMapper periodMapper;
    private final PeriodRepository periodRepository;
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    public DiplomaDto toDto(DiplomaEntity diplomaEntity){
        DiplomaDto diplomaDto = new DiplomaDto();

        diplomaDto.setDiplomaId(diplomaEntity.getDiplomaId());
        diplomaDto.setScore(diplomaEntity.getScore());
        diplomaDto.setStage(diplomaEntity.getStage());
        diplomaDto.setTaken(diplomaEntity.getTaken());
        diplomaDto.setVisibility(diplomaEntity.getVisibility());
        diplomaDto.setType(diplomaEntity.getType());
        diplomaDto.setDescription(diplomaEntity.getDescription());
        diplomaDto.setAbstractName(diplomaEntity.getAbstractDoc());
        diplomaDto.setKeywords(diplomaEntity.getKeyWords());
        diplomaDto.setTitle(diplomaEntity.getTitle());
        diplomaDto.setDetails(diplomaEntity.getDetails());
        diplomaDto.setNecessaryKnowledge(diplomaEntity.getNecessaryKnowledge());
        diplomaDto.setDifferentExpectations(diplomaEntity.getDifferentExpectations());
        diplomaDto.setPeriod(periodMapper.toDto(diplomaEntity.getPeriod()));
        diplomaDto.setBibliography(diplomaEntity.getBibliography());
        if(diplomaEntity.getTeacher() != null){
            diplomaDto.setTeacher(userMapper.toUserResponseDto(diplomaEntity.getTeacher()));
        }

        if(diplomaEntity.getStudent() != null){
            diplomaDto.setStudent(userMapper.toUserResponseDto(diplomaEntity.getStudent()));
        }

        return diplomaDto;
    }

    public List<DiplomaDto> toDtoList(List<DiplomaEntity> diplomaEntities){
        return diplomaEntities.stream().map(this::toDto).collect(Collectors.toList());
    }



    public DiplomaEntity toEntity(DiplomaDto diplomaDto){
        DiplomaEntity diplomaEntity = new DiplomaEntity();

        diplomaEntity.setDiplomaId(diplomaDto.getDiplomaId());
        diplomaEntity.setScore(diplomaDto.getScore());
        diplomaEntity.setStage(diplomaDto.getStage());
        diplomaEntity.setTaken(diplomaDto.getTaken());
        diplomaEntity.setVisibility(diplomaDto.getVisibility());
        diplomaEntity.setType(diplomaDto.getType());
        diplomaEntity.setDescription(diplomaDto.getDescription());
        diplomaEntity.setAbstractDoc(diplomaDto.getAbstractName());
        diplomaEntity.setKeyWords(diplomaDto.getKeywords());
        diplomaEntity.setTitle(diplomaDto.getTitle());
        diplomaEntity.setDetails(diplomaDto.getDetails());
        diplomaEntity.setNecessaryKnowledge(diplomaDto.getNecessaryKnowledge());
        diplomaEntity.setDifferentExpectations(diplomaDto.getDifferentExpectations());
        diplomaEntity.setBibliography(diplomaDto.getBibliography());
        diplomaEntity.setPeriod(periodMapper.toEntity(diplomaDto.getPeriod()));


        if(diplomaDto.getTeacher() != null){
            Optional<UserEntity> user = userRepository.findById(diplomaDto.getTeacher().getId());
            if(user.isEmpty()){
                throw new EntityNotFoundException("No user found with provided id at create course!");
            }
            diplomaEntity.setTeacher(user.get());
        }

        if(diplomaDto.getStudent() != null){
            Optional<UserEntity> user = userRepository.findById(diplomaDto.getTeacher().getId());
            if(user.isEmpty()){
                throw new EntityNotFoundException("No user found with provided id at create course!");
            }
            diplomaEntity.setStudent(user.get());
        }

        return diplomaEntity;
    }

    public List<DiplomaEntity> toEntityList(List<DiplomaDto> diplomaDtos){
        return diplomaDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
