package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.diplomaFiles.DiplomaFilesDto;
import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.DiplomaFilesEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.mapper.DiplomaFilesMapper;
import com.prismasolutions.DWbackend.repository.DiplomaFilesRepository;
import com.prismasolutions.DWbackend.repository.DiplomaRepository;
import com.prismasolutions.DWbackend.repository.UserRepository;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiplomaFilesServiceImpl implements DiplomaFilesService{

    private final DiplomaRepository diplomaRepository;
    private final Utility utility;
    private final DiplomaFilesRepository diplomaFilesRepository;
    private final UserRepository userRepository;
    private final DiplomaFilesMapper diplomaFilesMapper;

    @Override
    public DiplomaFilesDto create(MultipartFile file, Long diplomaID) throws IOException {
        if(diplomaID == null){
            throw new IllegalArgumentException("ID cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntityOptional = diplomaRepository.findById(diplomaID);

        if(diplomaEntityOptional.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }

        DiplomaFilesEntity diplomaFilesEntity = new DiplomaFilesEntity();

        String type = "";
        if(file.getContentType().equals("application/pdf")) {
            type = ".pdf";
        }
        if(file.getContentType().equals("application/zip") || file.getContentType().equals("application/x-zip-compressed")) {
            type = ".zip";
        }
        diplomaFilesEntity.setType(type);
        diplomaFilesEntity.setDiploma(diplomaEntityOptional.get());
        diplomaFilesEntity.setTitle(StringUtils.cleanPath(file.getOriginalFilename()));
        diplomaFilesEntity.setAuthor(utility.getCurrentUser());
        diplomaFilesEntity.setVisibility(0);
        diplomaFilesEntity.setPath(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/diplomaFiles/")
                .path(file.getOriginalFilename()+type)
                .toUriString());

        DiplomaFilesEntity newDiplomaFile = diplomaFilesRepository.save(diplomaFilesEntity);
        Path filePath  = Path.of("src/main/resources/diplomaFiles/"+diplomaEntityOptional.get().getDiplomaId());
        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        return diplomaFilesMapper.toDto(newDiplomaFile);
    }

    @Override
    public DiplomaFilesDto update(MultipartFile file, Long diplomaID) throws IOException {
        if(diplomaID == null){
            throw new IllegalArgumentException("ID cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntityOptional = diplomaRepository.findById(diplomaID);

        if(diplomaEntityOptional.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }

        DiplomaFilesEntity diplomaFilesEntity = diplomaFilesRepository.findByDiploma_DiplomaIdAndAuthor_Id(diplomaID,utility.getCurrentUser().getId());

        if(diplomaFilesEntity == null){
            throw new EntityNotFoundException("Entity not found!");
        }

        String type = "";
        if(file.getContentType().equals("application/pdf")) {
            type = "PDF";
        }
        if(file.getContentType().equals("application/zip") || file.getContentType().equals("application/x-zip-compressed")) {
            type = "ZIP";
        }
        diplomaFilesEntity.setType(type);
        diplomaFilesEntity.setDiploma(diplomaEntityOptional.get());
        diplomaFilesEntity.setTitle(StringUtils.cleanPath(file.getOriginalFilename()));
        diplomaFilesEntity.setAuthor(utility.getCurrentUser());
        diplomaFilesEntity.setVisibility(0);

        DiplomaFilesEntity newDiplomaFile = diplomaFilesRepository.save(diplomaFilesEntity);
        Path filePath  = Path.of("src/main/resources/diplomaFiles/"+diplomaEntityOptional.get().getDiplomaId()+"/");
        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        return diplomaFilesMapper.toDto(newDiplomaFile);
    }
}
