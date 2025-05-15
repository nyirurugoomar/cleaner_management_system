package com.cleaner.dto;

public class updateAttendanceDto {
    
    private String cleanerId;
    private String date;
    private String checkInTime;
    private String checkOutTime;
    private String location;
    private String attendanceStatus;
    private String note;

    public String getCleanerId() {
        return cleanerId;
    }

    public void setCleanerId(String cleanerId) {
        this.cleanerId = cleanerId;
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

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}
