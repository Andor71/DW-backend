package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.diplomaFiles.DiplomaFilesDto;
import com.prismasolutions.DWbackend.entity.DiplomaEntity;
import com.prismasolutions.DWbackend.entity.DiplomaFilesEntity;
import com.prismasolutions.DWbackend.entity.UserEntity;
import com.prismasolutions.DWbackend.enums.DiplomaFileType;
import com.prismasolutions.DWbackend.exception.NoAuthority;
import com.prismasolutions.DWbackend.exception.UserFriendlyException;
import com.prismasolutions.DWbackend.mapper.DiplomaFilesMapper;
import com.prismasolutions.DWbackend.repository.DiplomaFilesRepository;
import com.prismasolutions.DWbackend.repository.DiplomaRepository;
import com.prismasolutions.DWbackend.repository.UserRepository;
import com.prismasolutions.DWbackend.util.Utility;
import liquibase.pro.packaged.P;
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
    private final FileStorageService fileStorageService;

    @Override
    public DiplomaFilesDto createAbstract(MultipartFile file, Long diplomaID) throws IOException {
        if(diplomaID == null){
            throw new IllegalArgumentException("ID cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntityOptional = diplomaRepository.findById(diplomaID);

        if(diplomaEntityOptional.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }

        DiplomaFilesEntity diplomaFilesEntity = new DiplomaFilesEntity();

        diplomaFilesEntity.setType(DiplomaFileType.ABSTRACT);
        diplomaFilesEntity.setDiploma(diplomaEntityOptional.get());
        diplomaFilesEntity.setTitle(StringUtils.cleanPath(file.getOriginalFilename()));
        diplomaFilesEntity.setAuthor(utility.getCurrentUser());
        diplomaFilesEntity.setVisibility(0);
        diplomaFilesEntity.setPath(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/diplomaFiles/")
                .path(file.getOriginalFilename()+".pdf")
                .toUriString());

        DiplomaFilesEntity newDiplomaFile = diplomaFilesRepository.save(diplomaFilesEntity);
        Path filePath  = Path.of("src/main/resources/diplomaFiles/"+diplomaEntityOptional.get().getDiplomaId());
        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        return diplomaFilesMapper.toDto(newDiplomaFile);
    }

    @Override
    public DiplomaFilesDto updateAbstract(MultipartFile file, Long diplomaID) throws IOException {
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

        diplomaFilesEntity.setType(DiplomaFileType.ABSTRACT);
        diplomaFilesEntity.setDiploma(diplomaEntityOptional.get());
        diplomaFilesEntity.setTitle(StringUtils.cleanPath(file.getOriginalFilename()));
        diplomaFilesEntity.setAuthor(utility.getCurrentUser());
        diplomaFilesEntity.setVisibility(0);

        DiplomaFilesEntity newDiplomaFile = diplomaFilesRepository.save(diplomaFilesEntity);
        Path filePath  = Path.of("src/main/resources/diplomaFiles/"+diplomaEntityOptional.get().getDiplomaId()+"/");
        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);

        return diplomaFilesMapper.toDto(newDiplomaFile);
    }

    @Override
    public DiplomaFilesDto getByUserAndDiplomaID(Long diplomaID, Long userID) {

        if(diplomaID == null){
            throw new IllegalArgumentException("ID cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntityOptional = diplomaRepository.findById(diplomaID);

        if(diplomaEntityOptional.isEmpty()){
            throw new EntityNotFoundException("Entity not found!");
        }

        if(userID == null){
            throw new IllegalArgumentException("User id cannot be null!");
        }

        UserEntity user = userRepository.findById(userID).orElseThrow(()->{
            throw new EntityNotFoundException("Entity not found!");
        });

        DiplomaFilesEntity diplomaFiles= diplomaFilesRepository.findByDiploma_DiplomaIdAndAuthor_Id(diplomaID,userID);
        if(diplomaFiles == null){
            return null;
        }

        return diplomaFilesMapper.toDto(diplomaFiles);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null!");
        }

        DiplomaFilesEntity diplomaFilesEntity = diplomaFilesRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException("Entity not found!");
        });

        if(!utility.getCurrentUser().equals(diplomaFilesEntity.getAuthor())){
            throw new NoAuthority("No authority");
        }

        if(!fileStorageService.deleteDiplomaFile(id)){
            throw new UserFriendlyException("Server error cannot delete file!");
        }

        diplomaFilesRepository.delete(diplomaFilesEntity);

    }


}
