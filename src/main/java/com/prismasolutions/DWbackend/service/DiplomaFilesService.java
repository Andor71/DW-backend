package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.diplomaFiles.DiplomaFilesDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DiplomaFilesService {

    DiplomaFilesDto createAbstract(MultipartFile file, Long diplomaID) throws IOException;

    DiplomaFilesDto updateAbstract(MultipartFile file, Long diplomaID) throws IOException;

    DiplomaFilesDto getByUserAndDiplomaID(Long diplomaID,Long userID);

    void delete(Long id);
}
