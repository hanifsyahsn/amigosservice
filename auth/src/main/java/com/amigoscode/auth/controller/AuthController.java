package com.amigoscode.auth.controller;

import com.amigoscode.auth.request.LoginRequest;
import com.amigoscode.auth.request.RegistrationRequest;
import com.amigoscode.auth.response.LoginResponse;
import com.amigoscode.auth.serviceImpl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    private Map<String, Object> response = new HashMap<>();

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationRequest registrationRequest) {
        try {
            System.out.println(registrationRequest.getUsername());
            System.out.println(registrationRequest.getEmail());
            System.out.println(registrationRequest.getName());
            System.out.println(registrationRequest.getPassword());
            authService.register(registrationRequest);
            response.put("message", "User has been registered successfully.");
            response.put("status", HttpStatus.CREATED);
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.login(loginRequest);
            response.put("message", "Login is success.");
            response.put("status", HttpStatus.OK);
            response.put("data", loginResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
