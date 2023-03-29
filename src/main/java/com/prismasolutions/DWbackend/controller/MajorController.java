package com.prismasolutions.DWbackend.controller;


import com.prismasolutions.DWbackend.dto.errorResponse.ErrorResponseDto;
import com.prismasolutions.DWbackend.service.MajorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/major")
@CrossOrigin
@AllArgsConstructor
public class MajorController {
    private final MajorService majorService;
    @GetMapping("/get-all-without-period")
    public ResponseEntity<?> getAllWithoutPeriod(){
        try {
            return ResponseEntity.ok().body(majorService.getAllWithoutPeriod());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));

        }
    }
}
