package com.cleaner.model;

public enum Status {
    Present("Present"),
    Absent("Absent"),
    Late("Late"),
    Active("Active"),   
    Inactive("Inactive"),
    OnLeave("On Leave"),
    Suspended("Suspended"),
    Terminated("Terminated");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}