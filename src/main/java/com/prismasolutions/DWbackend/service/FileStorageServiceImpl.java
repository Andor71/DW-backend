package com.prismasolutions.DWbackend.service;

import com.prismasolutions.DWbackend.config.DiplomaStorageProperties;
import com.prismasolutions.DWbackend.config.FileStorageProperties;
import com.prismasolutions.DWbackend.dto.diplomaFiles.DiplomaFilesDto;
import com.prismasolutions.DWbackend.dto.document.DocumentDto;
import com.prismasolutions.DWbackend.entity.*;
import com.prismasolutions.DWbackend.enums.DiplomaFileType;
import com.prismasolutions.DWbackend.exception.FileStorageException;
import com.prismasolutions.DWbackend.exception.MyFileNotFoundException;
import com.prismasolutions.DWbackend.mapper.DiplomaFilesMapper;
import com.prismasolutions.DWbackend.mapper.DocumentMapper;
import com.prismasolutions.DWbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path fileStorageLocation;
    private final Path diplomaStorageLocation;
    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final YearRepository yearRepository;

    @Autowired
    private final DocumentMapper documentMapper;

    @Autowired
    private final DiplomaFilesMapper diplomaFilesMapper;

    @Autowired
    private final DiplomaRepository diplomaRepository;

    @Autowired
    private final DiplomaFilesRepository diplomaFilesRepository;

    @Autowired
    private final UserRepository userRepository;


    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties, DiplomaStorageProperties diplomaStorageProperties, DocumentRepository documentRepository, YearRepository yearRepository, DocumentMapper documentMapper, DiplomaFilesMapper diplomaFilesMapper, DiplomaRepository diplomaRepository, DiplomaFilesRepository diplomaFilesRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.yearRepository = yearRepository;
        this.documentMapper = documentMapper;
        this.diplomaFilesMapper = diplomaFilesMapper;
        this.diplomaRepository = diplomaRepository;
        this.diplomaFilesRepository = diplomaFilesRepository;
        this.userRepository = userRepository;
        try {
            this.diplomaStorageLocation = Paths.get(diplomaStorageProperties.getUploadDir()).toAbsolutePath().normalize();
            Files.createDirectories(this.diplomaStorageLocation);
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
                documentEntity = documentRepository.save(documentEntity);

            }else{
                DocumentEntity document = new DocumentEntity();
                document.setName(fileName);
                document.setYear(yearEntity.get());
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

    @Override
    public DiplomaFilesDto storeDiploma(MultipartFile file, Long diplomaID, Long userID) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null!");
        }

        if (!file.getContentType().equals("application/zip") && !file.getContentType().equals("application/x-zip-compressed")) {
            throw new IllegalArgumentException("Wrong file type");
        }

        try {
            if (diplomaID == null) {
                throw new IllegalArgumentException("ID cannot be null!");
            }

            Optional<DiplomaEntity> diplomaEntityOptional = diplomaRepository.findById(diplomaID);

            if (diplomaEntityOptional.isEmpty()) {
                throw new EntityNotFoundException("Entity not found!");
            }

            if (userID == null) {
                throw new IllegalArgumentException("User id cannot be null!");
            }

            UserEntity user = userRepository.findById(userID).orElseThrow(() -> {
                throw new EntityNotFoundException("Entity not found!");
            });

            String fileName = user.getFirstName() + diplomaID +".zip";

            DiplomaFilesEntity diplomaFilesEntity = diplomaFilesRepository.findByDiploma_DiplomaIdAndAuthor_Id(diplomaID,userID);

            if(diplomaFilesEntity == null){
                diplomaFilesEntity = new DiplomaFilesEntity();
            }

            diplomaFilesEntity.setType(DiplomaFileType.DIPLOMA);
            diplomaFilesEntity.setDiploma(diplomaEntityOptional.get());
            diplomaFilesEntity.setTitle(fileName);
            diplomaFilesEntity.setAuthor(user);
            diplomaFilesEntity.setVisibility(0);
            diplomaFilesEntity.setPath(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/diplomaFilesStudent/")
                    .path(fileName)
                    .toUriString());


            Path targetLocation = this.diplomaStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);


            return diplomaFilesMapper.toDto(diplomaFilesRepository.save(diplomaFilesEntity));
        }
             catch (Exception ex) {
        throw new FileStorageException("Could not store file, Please try again!");
        }
    }

    @Override
    public Resource loadDiploma(Long diplomaID, Long userID) {
        if (diplomaID == null) {
            throw new IllegalArgumentException("ID cannot be null!");
        }

        Optional<DiplomaEntity> diplomaEntityOptional = diplomaRepository.findById(diplomaID);

        if (diplomaEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Entity not found!");
        }

        if (userID == null) {
            throw new IllegalArgumentException("User id cannot be null!");
        }

        UserEntity user = userRepository.findById(userID).orElseThrow(() -> {
            throw new EntityNotFoundException("Entity not found!");
        });


        try {

            DiplomaFilesEntity diplomaFilesEntity = diplomaFilesRepository.findByDiploma_DiplomaIdAndAuthor_Id(diplomaID,userID);

            Path filePath = this.diplomaStorageLocation.resolve(diplomaFilesEntity.getTitle()).normalize();
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

    @Override
    public boolean deleteDiplomaFile(Long diplomaFilesId) {
        try {

            DiplomaFilesEntity diplomaFilesEntity = diplomaFilesRepository.findById(diplomaFilesId).orElseThrow((()->{
                throw new EntityNotFoundException("Entity not found!");
            }));

            Path filePath = this.diplomaStorageLocation.resolve(diplomaFilesEntity.getTitle()).normalize();

            return Files.deleteIfExists(filePath);

        } catch (Exception e) {
            throw new MyFileNotFoundException("File not found");
        }
    }
}
