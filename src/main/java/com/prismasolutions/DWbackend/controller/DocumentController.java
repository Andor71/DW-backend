package com.prismasolutions.DWbackend.controller;


import com.prismasolutions.DWbackend.dto.document.DocumentDto;
import com.prismasolutions.DWbackend.dto.errorResponse.ErrorResponseDto;
import com.prismasolutions.DWbackend.service.DocumentService;
import com.prismasolutions.DWbackend.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/document")
@CrossOrigin
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final FileStorageService fileStorageService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.ok().body(documentService.getAllDocumentsByYear());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {

            return ResponseEntity.ok().body(  documentService.delete(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @PostMapping("/uploadFile/{yearID}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable Long yearID) {
        try{
            return ResponseEntity.ok().body( fileStorageService.storeFile(file,yearID));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @PostMapping("/uploadMultipleFiles/{yearID}")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,@PathVariable Long yearID ) {
        try {

            List<DocumentDto> documentDtos = new ArrayList<>();
            for(MultipartFile file : files){
                    documentDtos.add( fileStorageService.storeFile(file,yearID));
            }
            return ResponseEntity.ok().body(documentDtos);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @GetMapping("/downloadFile/{documentID}")
    public ResponseEntity<?> downloadFile(@PathVariable Long documentID, HttpServletRequest request) {

        Resource resource = fileStorageService.loadFile(documentID);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}

