package com.cleaner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleaner.model.Clearner;
import com.cleaner.service.CleanerService;

@RestController
@RequestMapping("/cleaner")
public class CleanerController {

    @Autowired
    private CleanerService cleanerService;

    @GetMapping
    public List<Clearner> getAllCleaners() {
        return cleanerService.getAllCleaners();
    }

    @GetMapping("/{id}")
    public Clearner getCleanerById(@PathVariable String id) {
        return cleanerService.getCleanerById(id);
    }

    @PostMapping
    public Clearner createCleaner(@RequestBody Clearner cleaner) {
        return cleanerService.createCleaner(cleaner);
    }

    @PutMapping("/{id}")
    public Clearner updateCleaner(@PathVariable String id, @RequestBody Clearner cleaner) {
        return cleanerService.updateCleaner(id, cleaner);
    }

    @DeleteMapping("/{id}")
    public void deleteCleaner(@PathVariable String id) {
        cleanerService.deleteCleaner(id);
    }
    
    
    
}
