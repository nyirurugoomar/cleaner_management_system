package com.cleaner.model;

public enum Status {
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