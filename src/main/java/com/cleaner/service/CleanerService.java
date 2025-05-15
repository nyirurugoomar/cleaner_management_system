package com.cleaner.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaner.dto.updateCleanerDto;
import com.cleaner.model.Cleaner;
import com.cleaner.model.Gender;
import com.cleaner.model.Role;
import com.cleaner.model.Status;
import com.cleaner.repository.CleanerRepository;

@Service
public class CleanerService {
    
    @Autowired
    private CleanerRepository cleanerRepository;

    public List<Cleaner> getAllCleaners() {
        return cleanerRepository.findAll();
    }

    public Cleaner getCleanerById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Cleaner ID cannot be null or empty");
        }

        return cleanerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cleaner with ID " + id + " not found"));
    }

    public Cleaner createCleaner(Cleaner cleaner) {
        if (cleanerRepository.findByEmail(cleaner.getEmail()).isPresent()) {
            throw new RuntimeException("Cleaner already exists");
        }
        return cleanerRepository.save(cleaner);
    }

    public Cleaner updateCleaner(String id, updateCleanerDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Update data cannot be null");
        }
        
        Cleaner existingCleaner = cleanerRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Cleaner with ID " + id + " not found"));

        mapDtoToEntity(dto, existingCleaner);
        return cleanerRepository.save(existingCleaner);
    }
    
    private void mapDtoToEntity(updateCleanerDto dto, Cleaner cleaner) {
        // Only update fields that are not null in the DTO
        if (dto.getFullname() != null && !dto.getFullname().trim().isEmpty()) {
            cleaner.setFullname(dto.getFullname());
        }
        
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            cleaner.setEmail(dto.getEmail());
        }
        
        if (dto.getPhone() != null && !dto.getPhone().trim().isEmpty()) {
            cleaner.setPhone(dto.getPhone());
        }
        
        if (dto.getGender() != null && !dto.getGender().trim().isEmpty()) {
            try {
                cleaner.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid gender value. Allowed values are: MALE, FEMALE, OTHER");
            }
        }
        
        if (dto.getAddress() != null && !dto.getAddress().trim().isEmpty()) {
            cleaner.setAddress(dto.getAddress());
        }
        
        if (dto.getNationalId() != null) {
            cleaner.setNationalId(dto.getNationalId());
        }
        
        if (dto.getDateOfBirth() != null && !dto.getDateOfBirth().trim().isEmpty()) {
            cleaner.setDateOfBirth(dto.getDateOfBirth());
        }
        
        if (dto.getRole() != null && !dto.getRole().trim().isEmpty()) {
            try {
                cleaner.setRole(Role.valueOf(dto.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role value. Allowed values are: CLEANER, MANAGER, ADMIN");
            }
        }
        
        if (dto.getStatus() != null && !dto.getStatus().trim().isEmpty()) {
            try {
                // Status enum values are properly cased (Active, Inactive, etc.)
                cleaner.setStatus(Status.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status value. Allowed values are: Active, Inactive, OnLeave, Suspended, Terminated");
            }
        }
    }

    public void deleteCleaner(String id) {
        cleanerRepository.deleteById(id);
    }
}
