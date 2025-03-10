package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.LoginRequest;
import com.example.jobportalbackend.model.dto.LoginResponse;
import com.example.jobportalbackend.model.dto.RegisterRequest;
import com.example.jobportalbackend.model.dto.UserDTO;
import com.example.jobportalbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.authenticate(loginRequest));
    }
}

