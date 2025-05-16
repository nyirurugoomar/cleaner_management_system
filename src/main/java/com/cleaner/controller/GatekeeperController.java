package com.cleaner.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleaner.dto.CreateAttendanceDto;
import com.cleaner.dto.ErrorResponse;
import com.cleaner.model.Attendance;
import com.cleaner.model.AttendanceStatus;
import com.cleaner.model.Cleaner;
import com.cleaner.service.AttendanceService;
import com.cleaner.service.CleanerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/gatekeeper")
public class GatekeeperController {

    @Autowired
    private CleanerService cleanerService;
    
    @Autowired
    private AttendanceService attendanceService;
    
    // Only users with GATEKEEPER or ADMIN role can access these endpoints
    
    @GetMapping("/cleaners")
    @PreAuthorize("hasAnyRole('GATEKEEPER', 'ADMIN')")
    public ResponseEntity<List<Cleaner>> getAllCleaners() {
        return ResponseEntity.ok(cleanerService.getAllCleaners());
    }
    
    @PostMapping("/cleaners")
    @PreAuthorize("hasAnyRole('GATEKEEPER', 'ADMIN')")
    public ResponseEntity<?> createCleaner(@Valid @RequestBody Cleaner cleaner) {
        try {
            Cleaner createdCleaner = cleanerService.createCleaner(cleaner);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCleaner);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Failed to create cleaner", e.getMessage()));
        }
    }
    
    @GetMapping("/attendance")
    @PreAuthorize("hasAnyRole('GATEKEEPER', 'ADMIN')")
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }
    
    @PostMapping("/attendance")
    @PreAuthorize("hasAnyRole('GATEKEEPER', 'ADMIN')")
    public ResponseEntity<?> createAttendance(@Valid @RequestBody CreateAttendanceDto dto) {
        try {
            // Convert DTO to attendance and save it
            Attendance attendance = new Attendance();
            
            if (dto.getCleanerId() != null) {
                try {
                    Cleaner cleaner = cleanerService.getCleanerById(dto.getCleanerId());
                    attendance.setCleaner(cleaner);
                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Invalid cleaner", e.getMessage()));
                }
            }
            
            // Handle attendance status
            if (dto.getAttendanceStatus() != null) {
                try {
                    // Try to match by enum name first (e.g., "PRESENT")
                    try {
                        attendance.setStatus(AttendanceStatus.valueOf(dto.getAttendanceStatus().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        // If that fails, try to match by display name (e.g., "Present")
                        boolean matched = false;
                        for (AttendanceStatus status : AttendanceStatus.values()) {
                            if (status.getDisplayName().equalsIgnoreCase(dto.getAttendanceStatus())) {
                                attendance.setStatus(status);
                                matched = true;
                                break;
                            }
                        }
                        if (!matched) {
                            return ResponseEntity.badRequest()
                                .body(new ErrorResponse("Invalid status", 
                                    "Status must be one of: Present, Absent, Late"));
                        }
                    }
                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Invalid status", 
                            "Status must be one of: Present, Absent, Late"));
                }
            }
            
            // Handle date and times
            if (dto.getDate() != null) {
                try {
                    attendance.setDate(LocalDate.parse(dto.getDate()));
                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Invalid date format", 
                            "Date should be in format YYYY-MM-DD"));
                }
            }
            
            if (dto.getCheckInTime() != null) {
                try {
                    attendance.setCheckInTime(LocalDateTime.parse(dto.getCheckInTime()));
                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Invalid check-in time format", 
                            "Time should be in ISO format (e.g., 2025-05-15T08:00:00)"));
                }
            }
            
            if (dto.getCheckOutTime() != null) {
                try {
                    attendance.setCheckOutTime(LocalDateTime.parse(dto.getCheckOutTime()));
                } catch (Exception e) {
                    return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Invalid check-out time format", 
                            "Time should be in ISO format (e.g., 2025-05-15T17:00:00)"));
                }
            }
            
            // Handle other fields
            if (dto.getLocation() != null) {
                attendance.setLocation(dto.getLocation());
            }
            
            if (dto.getNote() != null) {
                attendance.setNote(dto.getNote());
            }
            
            Attendance createdAttendance = attendanceService.createAttendance(attendance);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Error creating attendance", e.getMessage()));
        }
    }
    
    @GetMapping("/attendance/{id}")
    @PreAuthorize("hasAnyRole('GATEKEEPER', 'ADMIN')")
    public ResponseEntity<?> getAttendanceById(@PathVariable String id) {
        try {
            Attendance attendance = attendanceService.getAttendanceById(id);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}