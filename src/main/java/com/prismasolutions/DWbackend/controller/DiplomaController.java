package com.prismasolutions.DWbackend.controller;


import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.diploma.ScoreDto;
import com.prismasolutions.DWbackend.dto.errorResponse.ErrorResponseDto;
import com.prismasolutions.DWbackend.exception.NoAuthority;
import com.prismasolutions.DWbackend.exception.UserFriendlyException;
import com.prismasolutions.DWbackend.service.DiplomaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/diploma")
@CrossOrigin
@AllArgsConstructor

public class DiplomaController {
    private final DiplomaService diplomaService;


    @PostMapping( value="/create")
    public ResponseEntity<?> create(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "DiplomaDto") DiplomaDto diplomaDto ){
        try {
            return ResponseEntity.ok().body(diplomaService.create(file,diplomaDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestPart(value = "file" , required=false)  MultipartFile file, @RequestPart(value = "DiplomaDto") DiplomaDto diplomaDto ){
        try {
            return ResponseEntity.ok().body(diplomaService.update(file,diplomaDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> create(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(diplomaService.getByID(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @GetMapping("/get-finished/{id}")
    public ResponseEntity<?> getFinished(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(diplomaService.getFinished(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.ok().body(diplomaService.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-my-diplomas")
    public ResponseEntity<?> getMyDiplomas(){
        try {
            return ResponseEntity.ok().body(diplomaService.getMyDiplomas());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-all-visible-diplomas-for-given-major")
    public ResponseEntity<?> getAllVisibleDiplomas(){
        try {
            return ResponseEntity.ok().body(diplomaService.getAllVisibleForGivenMajor());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            diplomaService.delete(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @PostMapping("/{diplomaID}/assign-to-diploma/{userID}")
    public ResponseEntity<?> assignToDiploma(@PathVariable Long diplomaID,@PathVariable Long userID){
        try {
            diplomaService.assignToDiploma(diplomaID,userID);
            return ResponseEntity.ok().build();
        }catch (UserFriendlyException e){
            return ResponseEntity.status(415).body(e);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-all-applied-diplomas-for-student")
    public ResponseEntity<?> getAllAppliedDiplomas(){
        try {
            return ResponseEntity.ok().body(diplomaService.getAllAppliedDiplomas());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @PostMapping("/change-applied-priority/{userID}")
    public ResponseEntity<?> changeAppliedPriority(@RequestBody List<DiplomaDto> diplomaDtos, @PathVariable Long userID){
        try {
            diplomaService.changeAppliedPriority(diplomaDtos,userID);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-by-id-for-student/{diplomaID}")
    public ResponseEntity<?> getByIdForStudent( @PathVariable Long diplomaID){
        try {
            return ResponseEntity.ok().body(diplomaService.getByIdForStudent(diplomaID));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-all-applied-diplomas-for-approving/{id}")
    public ResponseEntity<?> getAllDiplomaApplies(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(diplomaService.getAllDiplomaApplies(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @PatchMapping("/sort-students-for-diploma")
    public ResponseEntity<?> sortStudentsForDiploma(){
        try {
            diplomaService.sortStudentsForDiplomaManual();
            return ResponseEntity.ok().build();
        }catch (NoAuthority e){
            return ResponseEntity.status(403).body(new ErrorResponseDto(e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @PostMapping("/{diplomaID}/enable-student-diploma/{studentID}")
    public ResponseEntity<?> enableStudentDiploma(@PathVariable Long diplomaID, @PathVariable Long studentID){
        try {
            diplomaService.enableStudentDiploma(diplomaID,studentID);
            return ResponseEntity.ok().build();
        }catch (NoAuthority e){
            return ResponseEntity.status(403).body(new ErrorResponseDto(e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @PostMapping("/enable-all-student-diploma/{allaccepted}")
    public ResponseEntity<?> enableAllStudentDiploma(@PathVariable Boolean allaccepted){
        try {

            return ResponseEntity.ok().body(diplomaService.enableAllStudentDiploma(allaccepted));
        }catch (NoAuthority e){
            return ResponseEntity.status(403).body(new ErrorResponseDto(e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @GetMapping("/{diplomaID}/get-all-students-applied")
    public ResponseEntity<?> getAllStudentsApplied(@PathVariable Long diplomaID){
        try {
            return ResponseEntity.ok().body(diplomaService.getAllStudentsApplied(diplomaID));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @GetMapping("/get-current-diploma")
    public ResponseEntity<?> getAllStudentsApplied(){
        try {
            return ResponseEntity.ok().body(diplomaService.getCurrentDiploma());
        }
        catch (NoAuthority e){
            return ResponseEntity.status(403).body(new ErrorResponseDto(e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @PostMapping("/finalize-applies")
    public ResponseEntity<?> finalizeApplies(){
        try {
            diplomaService.finalizeApplies();
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-all-finished")
    public ResponseEntity<?> getAllFinished(){
        try {

            return ResponseEntity.ok().body(   diplomaService.getAllFinished());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @PostMapping("/set-score")
    public ResponseEntity<?> setScore(@RequestBody ScoreDto scoreDto){
        try {

            return ResponseEntity.ok().body(   diplomaService.setScore(scoreDto));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

}
