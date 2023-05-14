package com.prismasolutions.DWbackend.controller;

import com.prismasolutions.DWbackend.annotations.PeriodDateDependent;
import com.prismasolutions.DWbackend.config.TokenAuthenticationService;
import com.prismasolutions.DWbackend.config.UserAuthenticationProvider;
import com.prismasolutions.DWbackend.dto.diploma.DiplomaDto;
import com.prismasolutions.DWbackend.dto.errorResponse.ErrorResponseDto;
import com.prismasolutions.DWbackend.dto.user.UserLoginDto;
import com.prismasolutions.DWbackend.dto.user.UserResponseDto;
import com.prismasolutions.DWbackend.enums.PeriodEnums;
import com.prismasolutions.DWbackend.service.UserService;
import com.prismasolutions.DWbackend.util.Utility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private final UserService userService;
    private final Utility utility;

    @GetMapping("/health-check")
    @PeriodDateDependent(key = PeriodEnums.START_OF_ENTERING_TOPICS)
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userDto,
                                   HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken usr = new UsernamePasswordAuthenticationToken(userDto.getEmail(),
                    userDto.getPassword());

            Authentication auth = userAuthenticationProvider.authenticate(usr);
            tokenAuthenticationService.authenticationResponse(response, auth);

            SecurityContextHolder.getContext().setAuthentication(auth);

            return ResponseEntity.ok().body(userService.getCurrentUserDto());
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(Long id) {
        try {
            return ResponseEntity.ok().body(userService.getById(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok().body(userService.getAll());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-all-active")
    public ResponseEntity<?> getAllActive() {
        try {
            return ResponseEntity.ok().body(userService.getAllActiveStudents());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @GetMapping("/get-all-teachers")
    public ResponseEntity<?> getAllTeachers() {
        try {
            return ResponseEntity.ok().body(userService.getAllTeachers());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @PostMapping("/create-student")
    public ResponseEntity<?> createStudent(@RequestBody UserResponseDto userResponseDto) {
        try {
            return ResponseEntity.ok().body(userService.createStudent(userResponseDto));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @PatchMapping("/update-student")
    public ResponseEntity<?> updateStudent(@RequestBody UserResponseDto userResponseDto) {
        try {
            return ResponseEntity.ok().body(userService.updateStudent(userResponseDto));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            userService.deleteStudent(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(e.getMessage()));
        }
    }
    @PostMapping("/upload-students/{periodID}")
    public ResponseEntity<?> createStudentViaFile(@RequestPart(value = "file") MultipartFile file, @PathVariable Long periodID){
        try {
            return ResponseEntity.ok().body(userService.createStudentsViaFile(file,periodID));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/get-all-students-for-period/{periodID}")
    public ResponseEntity<?> getAllStudentsForPeriod( @PathVariable Long periodID){
        try {
            return ResponseEntity.ok().body(userService.getAllStudentsForPeriod(periodID));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/check-preconditions/{enums}")
    public ResponseEntity<?> checkPreconditions( @PathVariable PeriodEnums enums){
        try {
            return ResponseEntity.ok().body(utility.requestAccepted(enums));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
