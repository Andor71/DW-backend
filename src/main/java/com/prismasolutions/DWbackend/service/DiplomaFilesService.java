package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.diplomaFiles.DiplomaFilesDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DiplomaFilesService {

    DiplomaFilesDto create(MultipartFile file, Long diplomaID) throws IOException;

    DiplomaFilesDto update(MultipartFile file, Long diplomaID) throws IOException;
}
