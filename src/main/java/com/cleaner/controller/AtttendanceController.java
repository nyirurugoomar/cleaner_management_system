package com.cleaner.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleaner.dto.CreateAttendanceDto;
import com.cleaner.dto.ErrorResponse;
import com.cleaner.dto.updateAttendanceDto;
import com.cleaner.model.Attendance;
import com.cleaner.model.AttendanceStatus;
import com.cleaner.model.Cleaner;
import com.cleaner.service.AttendanceService;
import com.cleaner.repository.CleanerRepository;

@RestController
@RequestMapping("/attendance")
public class AtttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private CleanerRepository cleanerRepository;

    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable String id) {
        try {
            Attendance attendance = attendanceService.getAttendanceById(id);
            return ResponseEntity.ok(attendance);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createAttendance(@RequestBody CreateAttendanceDto dto) {
        try {
            Attendance attendance = new Attendance();
            
            // Create a Cleaner object with just the ID
            if (dto.getCleanerId() != null) {
                try {
                    Cleaner cleaner = cleanerRepository.findById(dto.getCleanerId())
                        .orElseThrow(() -> new NoSuchElementException("Cleaner with ID " + dto.getCleanerId() + " not found"));
                    attendance.setCleaner(cleaner);
                } catch (NoSuchElementException e) {
                    return ResponseEntity.badRequest()
                        .body(new ErrorResponse("Invalid cleaner", e.getMessage()));
                }
            }
            
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
            
            if (dto.getLocation() != null) {
                attendance.setLocation(dto.getLocation());
            }
            
            if (dto.getNote() != null) {
                attendance.setNote(dto.getNote());
            }
            
            Attendance createdAttendance = attendanceService.createAttendance(attendance);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendance);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Error creating attendance", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttendance(@PathVariable String id, @RequestBody updateAttendanceDto dto) {
        try {
            Attendance updatedAttendance = attendanceService.updateAttendace(id, dto);
            return ResponseEntity.ok(updatedAttendance);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Invalid update data", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable String id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}
