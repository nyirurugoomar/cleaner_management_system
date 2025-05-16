package com.cleaner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleaner.dto.ErrorResponse;
import com.cleaner.dto.LoginRequestDto;
import com.cleaner.dto.UserRegistrationDto;
import com.cleaner.model.User;
import com.cleaner.service.UserService;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@ConditionalOnProperty(name = "app.security.enabled", havingValue = "false", matchIfMissing = true)
public class DevAuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> devLogin(@Valid @RequestBody LoginRequestDto loginRequest) {
        try {
            // In development mode, just return a mock token response
            Map<String, Object> response = new HashMap<>();
            response.put("token", "dev-token-for-testing-only");
            response.put("username", loginRequest.getUsername());
            response.put("roles", Collections.singletonList("ROLE_USER"));
            response.put("message", "Development login - no security checks performed");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Login failed", e.getMessage()));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            User user = userService.registerUser(registrationDto);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Registration failed", e.getMessage()));
        }
    }
} 