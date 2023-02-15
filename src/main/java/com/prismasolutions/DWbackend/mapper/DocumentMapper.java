package com.prismasolutions.DWbackend.mapper;


import com.prismasolutions.DWbackend.dto.document.DocumentDto;
import com.prismasolutions.DWbackend.entity.DocumentEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DocumentMapper {

    private final YearMapper yearMapper;

    public DocumentEntity toEntity(DocumentDto documentDto){
        DocumentEntity documentEntity = new DocumentEntity();

        documentEntity.setDocumentId(documentDto.getDocumentId());
        documentEntity.setName(documentDto.getName());
        documentEntity.setPath(documentDto.getPath());
        documentEntity.setYear(yearMapper.toEntity(documentDto.getYear()));

        return documentEntity;
    }

    public List<DocumentEntity> toEntityList(List<DocumentDto> documentDtos){
        return documentDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
    public DocumentDto toDto(DocumentEntity documentEntity){
        DocumentDto documentDto = new DocumentDto();

        documentDto.setDocumentId(documentEntity.getDocumentId());
        documentDto.setName(documentEntity.getName());
        documentDto.setPath(documentEntity.getPath());
        documentDto.setYear(yearMapper.toDto(documentEntity.getYear()));

        return documentDto;
    }

    public List<DocumentDto> toDtoList(List<DocumentEntity> documentEntities){
        return documentEntities.stream().map(this::toDto).collect(Collectors.toList());
    }

}

