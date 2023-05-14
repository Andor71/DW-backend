package com.prismasolutions.DWbackend.controller;

import com.prismasolutions.DWbackend.dto.errorResponse.ErrorResponseDto;
import com.prismasolutions.DWbackend.dto.period.PeriodDto;
import com.prismasolutions.DWbackend.service.PeriodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/period")
@CrossOrigin
@AllArgsConstructor
public class PeriodController {

    private final PeriodService periodService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PeriodDto periodDto){
        try {
            return ResponseEntity.ok().body(periodService.create(periodDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody PeriodDto periodDto){
        try {
            return ResponseEntity.ok().body(periodService.update(periodDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllPeriods(){
        try {
            return ResponseEntity.ok().body(periodService.getAllActivePeriod());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {

            return ResponseEntity.ok().body(periodService.delete(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getByID(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(periodService.getById(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @GetMapping("/get-by-major-id/{id}")
    public ResponseEntity<?> getByMajorID(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(periodService.getPeriodByMajorId(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-all-period-by-year")
    public ResponseEntity<?> getAllPeriodByYear(){
        try {
            return ResponseEntity.ok().body(periodService.getAllPeriodsByYear());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-current-period-for-major/{majorID}")
    public ResponseEntity<?> getCurrentPeriodForMajor(@PathVariable Long majorID){
        try {
            return ResponseEntity.ok().body(periodService.getCurrentPeriodForMajor(majorID));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

}
