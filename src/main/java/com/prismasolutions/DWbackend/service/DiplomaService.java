package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.FinishedSDMappingDto.FinishedSDMappingDto;
import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.diploma.ScoreDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
//TODO Folyatni azzal hogy hogyan tolti fel a student a diplomat , megnezni hogy milyen typeot adunk neki , atirni abstractra es diplomara
//Ez utan mar csak a vissza adas kell , es letrehozni a guestet ott lehessen letolteni es a tanarnal
//Ezek utan atmenni a flowon es javitani a logikat
public interface DiplomaService {

    DiplomaDto create(MultipartFile file, DiplomaDto diplomaDto) throws IOException;
    DiplomaDto getByID(Long id);
    List<DiplomaDto> getAll();
    List<DiplomaDto> getMyDiplomas();
    void delete(Long id);
    DiplomaDto update(MultipartFile file,DiplomaDto diplomaDto) throws IOException;
    List<DiplomaDto> getAllVisible();
    void assignToDiploma(Long diplomaID,Long userID);
    List<DiplomaDto> getAllAppliedDiplomas();
    void changeAppliedPriority(List<DiplomaDto> diplomaDtos,Long userID);
    DiplomaDto getByIdForStudent(Long id);
    List<FinishedSDMappingDto> getAllDiplomaApplies(Long id);
    void sortStudentsForDiploma();

    void enableStudentDiploma(Long diplomaID, Long studentID);

    List<FinishedSDMappingDto> enableAllStudentDiploma(Boolean allaccepted);

    List<UserResponseDto> getAllStudentsApplied(Long diplomaID);

    List<DiplomaDto> getAllVisibleForGivenMajor();

    DiplomaDto getCurrentDiploma();

    void finalizeApplies();

    void sortStudentsForDiplomaManual();

    List<DiplomaDto> getAllFinished();

    DiplomaDto getFinished(Long id);

    DiplomaDto setScore(ScoreDto scoreDto);
}
