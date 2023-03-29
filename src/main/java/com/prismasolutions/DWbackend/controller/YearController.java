package com.prismasolutions.DWbackend.controller;

import com.prismasolutions.DWbackend.dto.errorResponse.ErrorResponseDto;
import com.prismasolutions.DWbackend.service.YearService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/year")
@CrossOrigin
@AllArgsConstructor
public class YearController {
    private final YearService yearService;
    @GetMapping("/get-current")
    public ResponseEntity<?> getcurrent() {
        try {
            return ResponseEntity.ok().body(yearService.getCurrent());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(yearService.getById(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> create() {
        try {
            return ResponseEntity.ok().body(yearService.create());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
}
