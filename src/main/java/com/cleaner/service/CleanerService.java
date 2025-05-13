package com.cleaner.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cleaner.model.Cleaner;
import com.cleaner.repository.CleanerRepository;

@Service
public class CleanerService {
    
    @Autowired
    private CleanerRepository cleanerRepository;

    public List<Cleaner> getAllCleaners() {
        return cleanerRepository.findAll();
    }

    public Cleaner getCleanerById(String id) {
        return cleanerRepository.findById(id).orElse(null);
    }

    public Cleaner createCleaner(Cleaner cleaner) {
        if (cleanerRepository.findByEmail(cleaner.getEmail()).isPresent()) {
            throw new RuntimeException("Cleaner already exists");
        }
        return cleanerRepository.save(cleaner);
    }

    public Cleaner updateCleaner(String id, Cleaner cleaner) {
        Cleaner existingCleaner = getCleanerById(id);
        if (existingCleaner == null) {
            throw new RuntimeException("Cleaner not found");
        }
        return cleanerRepository.save(cleaner);
    }

    public void deleteCleaner(String id) {
        cleanerRepository.deleteById(id);
    }
}
