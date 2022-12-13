package com.paresh.diff.dto;

/**
 * Created by Admin on 27-06-2017.
 */
public enum ChangeType {
    UPDATED("Updated"), NO_CHANGE("Unchanged"), ADDED("New"), DELETED("Deleted");

    private final String description;

    ChangeType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
