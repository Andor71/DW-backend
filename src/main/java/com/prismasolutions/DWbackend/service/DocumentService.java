package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.document.DocumentResponseDto;

import java.util.List;

public interface DocumentService {

    void delete(Long id);
    List<DocumentResponseDto> getAllDocumentsByYear();

}
