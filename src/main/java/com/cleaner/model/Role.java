package com.cleaner.model;

public enum Role {
    CLEANER("Cleaner"),    // Default role
    MANAGER("Manager"),
    ADMIN("Admin");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}