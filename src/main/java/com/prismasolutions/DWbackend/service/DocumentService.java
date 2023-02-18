package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.document.DocumentResponseDto;

import java.util.List;

public interface DocumentService {

    Long delete(Long id);
    List<DocumentResponseDto> getAllDocumentsByYear();

}
