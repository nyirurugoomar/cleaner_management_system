package com.cleaner.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cleaner.model.Clearner;
import com.cleaner.repository.CleanerRepository;

@Service
public class CleanerService {
    
    @Autowired
    private CleanerRepository cleanerRepository;

    public List<Clearner> getAllCleaners() {
        return cleanerRepository.findAll();
    }

    public Clearner getCleanerById(String id) {
        return cleanerRepository.findById(id).orElse(null);
    }

    public Clearner createCleaner(Clearner cleaner) {
        if (cleanerRepository.findByEmail(cleaner.getEmail()).isPresent()) {
            throw new RuntimeException("Cleaner is already exists");
        }
        return cleanerRepository.save(cleaner);

    }

    public Clearner updateCleaner(String id, Clearner cleaner) {
        Clearner existingCleaner = getCleanerById(id);
        if (existingCleaner == null) {
            throw new RuntimeException("Cleaner not found");
        }
        return cleanerRepository.save(cleaner);
    }

    public void deleteCleaner(String id) {
        cleanerRepository.deleteById(id);

    }
}
