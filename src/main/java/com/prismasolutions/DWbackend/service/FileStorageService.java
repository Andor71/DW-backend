package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.document.DocumentDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    DocumentDto storeFile(MultipartFile file, Long yearID);

    Resource loadFile(Long documentID);


}
