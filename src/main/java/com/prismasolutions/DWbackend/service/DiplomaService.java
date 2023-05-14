package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.FinishedSDMappingDto.FinishedSDMappingDto;
import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    List<FinishedSDMappingDto> getAllDiplomaApplies();
    void sortStudentsForDiploma();

    void enableStudentDiploma(Long diplomaID, Long studentID);

    List<FinishedSDMappingDto> enableAllStudentDiploma(Boolean allaccepted);

    List<UserResponseDto> getAllStudentsApplied(Long diplomaID);

    List<DiplomaDto> getAllVisibleForGivenMajor();

    DiplomaDto getCurrentDiploma();

    void finalizeApplies();
}
