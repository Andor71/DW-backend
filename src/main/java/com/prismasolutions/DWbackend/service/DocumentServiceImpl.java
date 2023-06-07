package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.document.DocumentResponseDto;
import com.prismasolutions.DWbackend.entity.DocumentEntity;
import com.prismasolutions.DWbackend.entity.YearEntity;
import com.prismasolutions.DWbackend.mapper.DocumentMapper;
import com.prismasolutions.DWbackend.mapper.YearMapper;
import com.prismasolutions.DWbackend.repository.DocumentRepository;
import com.prismasolutions.DWbackend.repository.YearRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService{

    private final YearRepository yearRepository;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final YearMapper yearMapper;
    @Override
    public Long delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        Optional<DocumentEntity> documentEntity = documentRepository.findById(id);

        if(documentEntity.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }

        documentRepository.delete(documentEntity.get());

        return id;
    }

    @Override
    public List<DocumentResponseDto> getAllDocumentsByYear() {

        List<DocumentResponseDto> documentsByYearDtos = new ArrayList<>();

        List<YearEntity> yearEntities = yearRepository.findAll();

        yearEntities.forEach((x)->{
            DocumentResponseDto documentsByYearDto = new DocumentResponseDto();
            documentsByYearDto.setYearDto(yearMapper.toDto(x));
            documentsByYearDto.setDocuments(documentMapper.toDtoList(documentRepository.findByYear_Id(x.getId())));
            documentsByYearDtos.add(documentsByYearDto);
        });

        return documentsByYearDtos;
    }
}
