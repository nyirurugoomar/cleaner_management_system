package com.cleaner.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleaner.dto.updateCleanerDto;
import com.cleaner.model.Cleaner;
import com.cleaner.service.CleanerService;

@RestController
@RequestMapping("/cleaner")
public class CleanerController {

    @Autowired
    private CleanerService cleanerService;

    @GetMapping
    public ResponseEntity<List<Cleaner>> getAllCleaners() {
        return ResponseEntity.ok(cleanerService.getAllCleaners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cleaner> getCleanerById(@PathVariable String id) {
        try {
            Cleaner cleaner = cleanerService.getCleanerById(id);
            return ResponseEntity.ok(cleaner);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCleaner(@RequestBody Cleaner cleaner) {
        try {
            Cleaner createdCleaner = cleanerService.createCleaner(cleaner);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCleaner);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Error creating cleaner", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCleaner(@PathVariable String id, @RequestBody updateCleanerDto dto) {
        try {
            Cleaner updatedCleaner = cleanerService.updateCleaner(id, dto);
            return ResponseEntity.ok(updatedCleaner);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Invalid data", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCleaner(@PathVariable String id) {
        try {
            cleanerService.deleteCleaner(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // Simple error response class
    private static class ErrorResponse {
        private String error;
        private String message;
        
        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }
        
        public String getError() {
            return error;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
