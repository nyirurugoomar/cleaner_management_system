package com.cleaner.model;

public enum AttendanceStatus {
    PRESENT("Present"),
    ABSENT("Absent"),
    LATE("Late");

    private final String displayName;

    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
