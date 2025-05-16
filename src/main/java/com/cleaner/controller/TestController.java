package com.cleaner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class TestController {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from Cleaner Management System!");
        response.put("status", "Server is running properly");
        return response;
    }
    
    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("appName", "Cleaner Management System");
        response.put("version", "1.0.0");
        response.put("description", "System for managing cleaning services");
        response.put("securityEnabled", false);
        return response;
    }
} 