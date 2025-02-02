package com.portfolioapi.controller;

import com.portfolioapi.dao.request.AuthRequest;
import com.portfolioapi.dao.request.UserRequest;
import com.portfolioapi.dao.response.JwtResponse;
import com.portfolioapi.dao.response.UserResponse;
import com.portfolioapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticateAndGetToken(authRequest));
    }

    @PostMapping("register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(authService.registerUser(userRequest));
    }
}
