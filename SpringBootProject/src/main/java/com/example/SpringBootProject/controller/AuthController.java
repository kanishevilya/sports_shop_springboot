package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.dto.Authentication.JwtTokenDTO;
import com.example.SpringBootProject.dto.Authentication.LoginRequestDTO;
import com.example.SpringBootProject.dto.Authentication.RegisterRequestDTO;
import com.example.SpringBootProject.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "Controller responsible for handling user authentication processes, including registration and login")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody RegisterRequestDTO signRequestDto) {
        authenticationService.registerUser(signRequestDto);
    }

    @PostMapping("/signin")
    public JwtTokenDTO authUser(@RequestBody LoginRequestDTO loginRequestDto) {
        return authenticationService.fullAuthUserWithBan(loginRequestDto);
    }

}
