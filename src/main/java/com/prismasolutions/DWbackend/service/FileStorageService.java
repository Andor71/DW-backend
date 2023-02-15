package com.prismasolutions.DWbackend.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile file, Long yearID);

    Resource loadFile(String fileName);


}
