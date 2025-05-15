package com.cleaner.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cleaner.dto.updateAttendanceDto;
import com.cleaner.model.Attendance;
import com.cleaner.model.AttendanceStatus;
import com.cleaner.model.Cleaner;
import com.cleaner.repository.AttendanceRepository;
import com.cleaner.repository.CleanerRepository;

@Service
public class AttendanceService {
    
    @Autowired
    private AttendanceRepository attendanceRepository;
    
    @Autowired
    private CleanerRepository cleanerRepository;

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(String id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Attendance with ID " + id + " not found"));
    }

    public Attendance createAttendance(Attendance attendance) {
        // Ensure cleaner reference is valid
        if (attendance.getCleaner() != null && attendance.getCleaner().getId() != null) {
            String cleanerId = attendance.getCleaner().getId();
            // Fetch full cleaner entity from database to ensure proper reference
            Cleaner cleaner = cleanerRepository.findById(cleanerId)
                .orElseThrow(() -> new NoSuchElementException("Cleaner with ID " + cleanerId + " not found"));
            attendance.setCleaner(cleaner);
        }
        
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendace(String id, updateAttendanceDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Update data cannot be null");
        }

        Attendance existingAttendance = attendanceRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Attendance with ID " + id + " not found"));  
        
        // Update cleaner reference if provided
        if (dto.getCleanerId() != null) {
            Cleaner cleaner = cleanerRepository.findById(dto.getCleanerId())
                .orElseThrow(() -> new NoSuchElementException("Cleaner with ID " + dto.getCleanerId() + " not found"));
            existingAttendance.setCleaner(cleaner);
        }
        
        // Update the attendance with values from the DTO
        if (dto.getAttendanceStatus() != null) {
            try {
                // Try to match by enum name first (e.g., "PRESENT")
                existingAttendance.setStatus(AttendanceStatus.valueOf(dto.getAttendanceStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // If that fails, try to match by display name (e.g., "Present")
                boolean matched = false;
                for (AttendanceStatus status : AttendanceStatus.values()) {
                    if (status.getDisplayName().equalsIgnoreCase(dto.getAttendanceStatus())) {
                        existingAttendance.setStatus(status);
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    throw new IllegalArgumentException("Invalid status. Status must be one of: Present, Absent, Late");
                }
            }
        }
        
        if (dto.getDate() != null) {
            existingAttendance.setDate(LocalDate.parse(dto.getDate()));
        }
        
        if (dto.getLocation() != null) {
            existingAttendance.setLocation(dto.getLocation());
        }
        
        if (dto.getNote() != null) {
            existingAttendance.setNote(dto.getNote());
        }
        
        if (dto.getCheckInTime() != null) {
            // Add check-in time logic here if needed
            existingAttendance.setCheckInTime(LocalDateTime.parse(dto.getCheckInTime()));
        }
        
        if (dto.getCheckOutTime() != null) {
            // Add check-out time logic here if needed
            existingAttendance.setCheckOutTime(LocalDateTime.parse(dto.getCheckOutTime()));
        }
        
        // Save and return the updated attendance
        return attendanceRepository.save(existingAttendance);
    }
    
    public void deleteAttendance(String id) {
        if (!attendanceRepository.existsById(id)) {
            throw new NoSuchElementException("Attendance with ID " + id + " not found");
        }
        attendanceRepository.deleteById(id);
    }
}
