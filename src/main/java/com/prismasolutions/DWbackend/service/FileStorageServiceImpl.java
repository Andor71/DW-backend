package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.config.FileStorageProperties;
import com.prismasolutions.DWbackend.dto.document.DocumentDto;
import com.prismasolutions.DWbackend.entity.DocumentEntity;
import com.prismasolutions.DWbackend.entity.YearEntity;
import com.prismasolutions.DWbackend.exception.FileStorageException;
import com.prismasolutions.DWbackend.exception.MyFileNotFoundException;
import com.prismasolutions.DWbackend.mapper.DocumentMapper;
import com.prismasolutions.DWbackend.repository.DocumentRepository;
import com.prismasolutions.DWbackend.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageServiceImpl implements FileStorageService{
    private final Path fileStorageLocation;
    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final YearRepository yearRepository;

    @Autowired
    private final DocumentMapper documentMapper;


    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties, DocumentRepository documentRepository, YearRepository yearRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.yearRepository = yearRepository;
        this.documentMapper = documentMapper;
        try {
            this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                    .toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    @Override
    public DocumentDto storeFile(MultipartFile file, Long yearID) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Optional<YearEntity> yearEntity = yearRepository.findById(yearID);

            if(yearEntity.isEmpty()){
                throw new EntityNotFoundException("No Entity found!");
            }


            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            DocumentEntity documentEntity = documentRepository.findByNameAndYear_Id(fileName,yearEntity.get().getId());

            if(documentEntity != null){
                documentEntity.setName(fileName);
                documentEntity.setYear(yearEntity.get());
//                documentEntity.setPath(targetLocation.toString());
                documentEntity = documentRepository.save(documentEntity);

            }else{
                DocumentEntity document = new DocumentEntity();
                document.setName(fileName);
                document.setYear(yearEntity.get());
//                document.setPath(targetLocation.toString());
                documentEntity = documentRepository.save(document);
            }


            return documentMapper.toDto(documentEntity);
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }
    }
    @Override
    public Resource loadFile(Long documentID) {
        if(documentID == null){
            throw new IllegalArgumentException("Id cannot be null!");
        }

        DocumentEntity documentEntity = documentRepository.findById(documentID).orElseThrow(()->{
            throw new IllegalArgumentException("ID cannot be null!");
        });

        try {
            Path filePath = this.fileStorageLocation.resolve(documentEntity.getName()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found ");
            }
        } catch (Exception e) {
            throw new MyFileNotFoundException("File not found");
        }
    }
}
