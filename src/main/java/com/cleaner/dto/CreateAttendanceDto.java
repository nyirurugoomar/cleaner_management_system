package com.cleaner.dto;

public class CreateAttendanceDto {
    private CleanerIdDto cleaner;
    private String attendanceStatus;
    private String date;
    private String checkInTime;
    private String checkOutTime;
    private String location;
    private String note;
    
    public static class CleanerIdDto {
        private String id;
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
    }
    
    public CleanerIdDto getCleaner() {
        return cleaner;
    }
    
    public void setCleaner(CleanerIdDto cleaner) {
        this.cleaner = cleaner;
    }
    
    public String getCleanerId() {
        return cleaner != null ? cleaner.getId() : null;
    }
    
    public String getAttendanceStatus() {
        return attendanceStatus;
    }
    
    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getCheckInTime() {
        return checkInTime;
    }
    
    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }
    
    public String getCheckOutTime() {
        return checkOutTime;
    }
    
    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
} 