package com.prismasolutions.DWbackend.controller;

import com.prismasolutions.DWbackend.dto.diplomaFiles.DiplomaFilesDto;
import com.prismasolutions.DWbackend.dto.errorResponse.ErrorResponseDto;
import com.prismasolutions.DWbackend.service.DiplomaFilesService;
import com.prismasolutions.DWbackend.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/diploma-file")
@CrossOrigin
@AllArgsConstructor
public class DiplomaFileController {
    private final DiplomaFilesService diplomaFilesService;
    private final FileStorageService fileStorageService;

    @PostMapping ("/{diplomaID}/upload/{userID}")
    public ResponseEntity<?> upload(@RequestPart(value = "file") MultipartFile file, @PathVariable Long diplomaID, @PathVariable Long userID){
        try {
            return ResponseEntity.ok().body(fileStorageService.storeDiploma(file,diplomaID,userID));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @GetMapping("/{diplomaID}/download-diploma-file/{userID}")
    public ResponseEntity<?> downloadFile(@PathVariable Long diplomaID,@PathVariable Long userID, HttpServletRequest request) {

        Resource resource = fileStorageService.loadDiploma(diplomaID,userID);

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
    @GetMapping("/{diplomaID}/get-diploma-file/{userID}")
    public ResponseEntity<?> upload(@PathVariable Long diplomaID, @PathVariable Long userID){
        try {
            return ResponseEntity.ok().body(diplomaFilesService.getByUserAndDiplomaID(diplomaID,userID));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            diplomaFilesService.delete(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
}
