package com.prismasolutions.DWbackend.controller;

import com.prismasolutions.DWbackend.config.TokenAuthenticationService;
import com.prismasolutions.DWbackend.config.UserAuthenticationProvider;
import com.prismasolutions.DWbackend.dto.user.UserLoginDto;
import com.prismasolutions.DWbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/health-check")
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


}
