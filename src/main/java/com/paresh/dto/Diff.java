package com.paresh.dto;

import com.paresh.constants.Constants;

import java.util.List;

/**
 * Created by Admin on 27-06-2017.
 */
public class Diff {
    private ChangeType changeType;
    private String fieldDescription;
    private String before;
    private String after;
    private String identifier;
    private List<Diff> childDiffs;

    public Diff(ChangeType changeType, String fieldDescription, String before, String after, String identifier) {
        this.changeType = changeType;
        this.fieldDescription = fieldDescription;
        this.before = before;
        this.after = after;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public List<Diff> getChildDiffs() {
        return childDiffs;
    }

    public void setChildDiffs(List<Diff> childDiffs) {
        this.childDiffs = childDiffs;
    }

    @Override
    public String toString() {
        return identifier != null && !identifier.isEmpty() ? "{" +
                "changeType=" + changeType +
                ", fieldDescription=" + fieldDescription + " " +
                ", before=" + before +
                ", after=" + after +
                ", identifier=" + identifier +
                '}' : "changeType=" + changeType +
                ", fieldDescription=" + fieldDescription + " " +
                ", before=" + before +
                ", after=" + after +
                '}';
    }

    private String getChildDeltaString() {
        StringBuilder stringBuilder = new StringBuilder(Constants.BLANK);
        if (childDiffs != null && childDiffs.size() > 0) {
            for (Diff child : childDiffs) {
                stringBuilder.append(child);
            }
        }
        return stringBuilder.toString();
    }

}
