package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.document.DocumentResponseDto;
import com.prismasolutions.DWbackend.dto.year.YearDto;
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
import java.util.Calendar;
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
    public void delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        Optional<DocumentEntity> documentEntity = documentRepository.findById(id);

        if(documentEntity.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }

        documentRepository.delete(documentEntity.get());
    }

    @Override
    public List<DocumentResponseDto> getAllDocumentsByYear() {

        int startYear = Calendar.getInstance().get(Calendar.YEAR);
        int endYear =Calendar.getInstance().get(Calendar.YEAR) +1;

        ArrayList<DocumentResponseDto> documentResponseDtos = new ArrayList<>();

        for(int i = 0; i <2 ; i++){
            // Get year from DB , if null means no years was creted so return this section;

            YearEntity yearEntity = yearRepository.findByStartYearAndEndYear(startYear,endYear);
            if(yearEntity == null){
                return documentResponseDtos;
            }
            YearDto yearDto = yearMapper.toDto(yearEntity);

            List<DocumentEntity> documentEntities = documentRepository.findByYear_Id(yearDto.getId());

            DocumentResponseDto documentResponseDto = new DocumentResponseDto();

            documentResponseDto.setYearDto(yearDto);
            documentResponseDto.setDocuments(documentMapper.toDtoList(documentEntities));

            documentResponseDtos.add(documentResponseDto);
            startYear++;
            endYear++;

        }

        return documentResponseDtos;
    }
}
