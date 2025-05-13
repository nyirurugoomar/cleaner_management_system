package com.cleaner.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "attendances")
public class Attendance {

    @Id
    private String id;

    @DBRef
    private Cleaner cleaner;

    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    private Location location;

    private AttendanceStatus status = AttendanceStatus.PRESENT;
}
